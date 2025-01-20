package com.devicemanager.app.dto;

import com.devicemanager.app.enums.StateEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.UUID;

@Builder
public record DeviceStateRequestDTO (UUID id, @JsonProperty("device-name") String deviceName, StateEnum state){
}
