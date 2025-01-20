package com.devicemanager.app.repository;

import com.devicemanager.app.entity.DeviceApprovalRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DeviceApprovalRequestRepository extends JpaRepository<DeviceApprovalRequestEntity, UUID> {
    List<DeviceApprovalRequestEntity> findByDeviceId(UUID deviceId);
}
