CREATE SCHEMA IF NOT EXISTS dvc;

CREATE TABLE dvc.devices (
    id UUID DEFAULT gen_random_uuid(),
    name VARCHAR(50) NOT NULL,
    brand VARCHAR(50)  NOT NULL,
    state VARCHAR(9) CHECK (state IN ('AVAILABLE', 'IN_USE', 'INACTIVE')) NOT NULL ,
    created_date TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
)