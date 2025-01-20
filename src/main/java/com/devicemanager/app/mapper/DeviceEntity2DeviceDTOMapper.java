package com.devicemanager.app.mapper;

import com.devicemanager.app.dto.DeviceDTO;
import com.devicemanager.app.entity.DeviceEntity;

import java.util.function.Function;

public class DeviceEntity2DeviceDTOMapper implements Function<DeviceEntity, DeviceDTO> {

    @Override
    public DeviceDTO apply(DeviceEntity deviceEntity) {
        return DeviceDTO.builder()
                .id(deviceEntity.getId())
                .name(deviceEntity.getName())
                .brand(deviceEntity.getBrand())
                .stateEnum(deviceEntity.getDeviceStateEntity().getState())
                .build();
    }
}
