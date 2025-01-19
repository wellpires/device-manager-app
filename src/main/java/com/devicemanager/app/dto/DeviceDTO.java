package com.devicemanager.app.dto;

import com.devicemanager.app.enums.StateEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.UUID;

@Builder
@JsonInclude(JsonInclude. Include. NON_NULL)
public record DeviceDTO(UUID id, String name, String brand, @JsonProperty("state") StateEnum stateEnum) {
}
