package com.devicemanager.app.dto.request;

import com.devicemanager.app.dto.DeviceStateDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public record DeviceStateRequest (@JsonProperty("device-state") DeviceStateDTO deviceStateDTO) {
}
