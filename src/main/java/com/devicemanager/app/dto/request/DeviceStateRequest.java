package com.devicemanager.app.dto.request;

import com.devicemanager.app.dto.DeviceStateDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record DeviceStateRequest (@JsonProperty("device-state") DeviceStateDTO deviceStateDTO) {
}
