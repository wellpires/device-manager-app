package com.devicemanager.app.mapper;

import com.devicemanager.app.dto.DeviceStateRequestDTO;
import com.devicemanager.app.entity.DeviceApprovalRequestEntity;

import java.util.function.Function;

public class DeviceApprovalRequestEntity2DeviceStateRequestDTOMapper implements Function<DeviceApprovalRequestEntity, DeviceStateRequestDTO> {
    @Override
    public DeviceStateRequestDTO apply(DeviceApprovalRequestEntity deviceApprovalRequestEntity) {

        return DeviceStateRequestDTO.builder()
                .id(deviceApprovalRequestEntity.getId())
                .deviceName(deviceApprovalRequestEntity.getDeviceEntity().getName())
                .state(deviceApprovalRequestEntity.getDeviceStateEntity().getState())
                .build();
    }
}
