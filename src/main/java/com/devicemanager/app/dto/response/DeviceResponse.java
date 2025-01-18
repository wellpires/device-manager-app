package com.devicemanager.app.dto.response;

import com.devicemanager.app.dto.DeviceDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record DeviceResponse (@JsonProperty("device") DeviceDTO deviceDTO) {
}
