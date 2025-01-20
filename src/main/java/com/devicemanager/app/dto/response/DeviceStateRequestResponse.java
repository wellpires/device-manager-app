package com.devicemanager.app.dto.response;

import com.devicemanager.app.dto.DeviceStateRequestDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record DeviceStateRequestResponse (@JsonProperty("state-requests") List<DeviceStateRequestDTO> deviceStateRequestDTOs) {
}
