package com.devicemanager.app.dto;

import lombok.Builder;

@Builder
public record DeviceDTO(String name, String brand) {
}
