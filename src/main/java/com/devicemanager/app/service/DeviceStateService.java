package com.devicemanager.app.service;

import com.devicemanager.app.dto.DeviceStateDTO;
import com.devicemanager.app.enums.StateEnum;

public interface DeviceStateService {
    DeviceStateDTO findByName(StateEnum stateEnum);
}
