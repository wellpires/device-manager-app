CREATE SCHEMA IF NOT EXISTS dmn;

CREATE TABLE dmn.device_states (
    id UUID DEFAULT gen_random_uuid(),
    name VARCHAR(9) CHECK (name IN ('AVAILABLE', 'IN_USE', 'INACTIVE')) NOT NULL,
    created_date TIMESTAMP DEFAULT current_timestamp NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO dmn.device_states (id, name) VALUES ('1b55427c-b5db-4318-bd6b-3b5a505651db', 'AVAILABLE');
INSERT INTO dmn.device_states (id, name) VALUES ('ee132a85-0269-4367-a1a8-567ae526c953', 'IN_USE');
INSERT INTO dmn.device_states (id, name) VALUES ('d31e9707-7d94-4f31-8af5-fcdfd1ccdc02', 'INACTIVE');

CREATE TABLE dvc.device_approval_requests(
    id UUID DEFAULT gen_random_uuid(),
    device_id UUID NOT NULL UNIQUE,
    state_id UUID NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_device
            FOREIGN KEY(device_id)
            REFERENCES dvc.devices(id)
            ON DELETE CASCADE,
    CONSTRAINT fk_state
            FOREIGN KEY(state_id)
            REFERENCES dmn.device_states(id)
            ON DELETE CASCADE

);

ALTER TABLE dvc.devices DROP COLUMN state;
ALTER TABLE dvc.devices ADD COLUMN state_id UUID DEFAULT '1b55427c-b5db-4318-bd6b-3b5a505651db' NOT NULL;
ALTER TABLE dvc.devices ADD CONSTRAINT fk_state
                        FOREIGN KEY(state_id)
                        REFERENCES dmn.device_states(id)
                        ON DELETE CASCADE;