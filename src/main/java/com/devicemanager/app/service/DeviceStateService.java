package com.devicemanager.app.service;

import com.devicemanager.app.dto.DeviceStateDTO;
import com.devicemanager.app.enums.StateEnum;

import java.util.UUID;

public interface DeviceStateService {
    DeviceStateDTO findByName(StateEnum stateEnum);

    DeviceStateDTO findById(UUID stateId);
}
