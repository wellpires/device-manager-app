package com.devicemanager.app.repository;

import com.devicemanager.app.entity.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeviceRepository extends JpaRepository<DeviceEntity, UUID> {
}
