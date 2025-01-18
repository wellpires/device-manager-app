package com.devicemanager.app.exception;

import lombok.Getter;

import java.util.UUID;

public class DeviceNotFoundException extends RuntimeException {

    public DeviceNotFoundException(UUID deviceId){
        super(String.format("Device Id %s not found!", deviceId.toString()));
    }

}
