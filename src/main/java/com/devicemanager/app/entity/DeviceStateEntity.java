package com.devicemanager.app.entity;

import com.devicemanager.app.enums.StateEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Table(schema = "dmn", name = "device_states")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceStateEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private StateEnum state;

}
