package com.devicemanager.app.controller.resource;

import com.devicemanager.app.dto.DeviceDTO;
import com.devicemanager.app.dto.request.DeviceRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface DeviceResource {

    @Operation(summary = "Create new Device")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Device created!"),
            @ApiResponse(responseCode = "500", description = "Internal server error!")})
    ResponseEntity<Void> create(DeviceRequest deviceRequest);

    @Operation(summary = "Fetch all Devices")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetch all devices!"),
            @ApiResponse(responseCode = "500", description = "Internal server error!")})
    Page<DeviceDTO> list(Pageable pageable);

}
