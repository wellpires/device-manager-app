package com.devicemanager.app.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record DeviceDTO(UUID id, String name, String brand) {
}
