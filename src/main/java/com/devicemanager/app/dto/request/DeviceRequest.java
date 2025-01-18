package com.devicemanager.app.dto.request;

import com.devicemanager.app.dto.DeviceDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record DeviceRequest(@JsonProperty("device") DeviceDTO deviceDTO) {
}
