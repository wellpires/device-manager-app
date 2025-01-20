package com.devicemanager.app.mapper;

import com.devicemanager.app.dto.DeviceStateDTO;
import com.devicemanager.app.entity.DeviceStateEntity;

import java.util.function.Function;

public class DeviceStateEntity2DeviceStateDTOMapper implements Function<DeviceStateEntity, DeviceStateDTO> {

    @Override
    public DeviceStateDTO apply(DeviceStateEntity deviceStateEntity) {
        return DeviceStateDTO.builder()
                .id(deviceStateEntity.getId())
                .name(deviceStateEntity.getState())
                .build();
    }
}
