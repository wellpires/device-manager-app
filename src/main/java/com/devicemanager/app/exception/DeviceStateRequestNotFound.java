package com.devicemanager.app.exception;

import java.util.UUID;

public class DeviceStateRequestNotFound extends RuntimeException {

    public DeviceStateRequestNotFound(UUID id) {
        super(String.format("Device Request approval %s not found!", id.toString()));
    }

}
