package com.devicemanager.app.exception;

import com.devicemanager.app.enums.StateEnum;

import java.util.UUID;

public class DeviceStateNotFoundException extends RuntimeException {

    public DeviceStateNotFoundException(StateEnum stateEnum) {
        super(String.format("Device status %s not found", stateEnum.name()));
    }

    public DeviceStateNotFoundException(UUID stateId) {
        super(String.format("Device status %s not found", stateId));
    }
}
