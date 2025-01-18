package com.devicemanager.app.entity;

import com.devicemanager.app.enums.StateEnum;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(schema = "dvc", name = "devices")
@Entity
@Data
@Builder
public class DeviceEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "brand")
    private String brand;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private StateEnum state;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;

}
