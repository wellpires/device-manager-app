package com.devicemanager.app.entity;

import com.devicemanager.app.enums.StateEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(schema = "dvc", name = "devices")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "brand")
    private String brand;

    @Column(name = "state_id")
    private UUID stateId;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "state_id", updatable = false, insertable = false)
    private DeviceStateEntity deviceStateEntity;

}
