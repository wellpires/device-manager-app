package com.devicemanager.app.service;

import com.devicemanager.app.dto.DeviceDTO;
import com.devicemanager.app.dto.DeviceStateRequestDTO;
import com.devicemanager.app.enums.StateEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface DeviceService {

    UUID create(DeviceDTO deviceDTO);

    Page<DeviceDTO> list(Pageable pageable);

    DeviceDTO find(UUID id);

    void delete(UUID id);

    void update(UUID id, DeviceDTO deviceDTO);

    void changeState(UUID id, StateEnum stateEnum);

    void changeState(UUID id, UUID stateId);
}
