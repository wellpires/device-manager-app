package com.devicemanager.app.dto.response;

import lombok.Builder;

@Builder
public record ErrorResponse(String message) {
}
