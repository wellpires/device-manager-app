package com.devicemanager.app.dto;

import com.devicemanager.app.enums.StateEnum;
import lombok.Builder;

import java.util.UUID;

@Builder
public record DeviceStateDTO(UUID id, StateEnum name) {
}
