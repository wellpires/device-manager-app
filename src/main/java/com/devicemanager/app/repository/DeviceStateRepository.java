package com.devicemanager.app.repository;

import com.devicemanager.app.entity.DeviceStateEntity;
import com.devicemanager.app.enums.StateEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DeviceStateRepository extends JpaRepository<DeviceStateEntity, UUID> {

    Optional<DeviceStateEntity> findByState(StateEnum stateEnum);

}
