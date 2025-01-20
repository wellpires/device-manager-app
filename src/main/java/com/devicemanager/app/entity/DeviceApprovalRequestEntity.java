package com.devicemanager.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Table(schema = "dvc", name = "device_approval_requests")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceApprovalRequestEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "device_id")
    private UUID deviceId;

    @Column(name = "state_id")
    private UUID stateId;

    @ManyToOne
    @JoinColumn(name = "device_id", updatable = false, insertable = false)
    private DeviceEntity deviceEntity;

    @ManyToOne
    @JoinColumn(name = "state_id", updatable = false, insertable = false)
    private DeviceStateEntity deviceStateEntity;

}
