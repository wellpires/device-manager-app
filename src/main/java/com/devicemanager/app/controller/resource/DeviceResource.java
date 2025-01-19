package com.devicemanager.app.controller.resource;

import com.devicemanager.app.dto.DeviceDTO;
import com.devicemanager.app.dto.request.DeviceRequest;
import com.devicemanager.app.dto.response.DeviceResponse;
import com.devicemanager.app.enums.StateEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

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

    @Operation(summary = "Fetch a single device")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device found"),
            @ApiResponse(responseCode = "404", description = "Device not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error!")})
    ResponseEntity<DeviceResponse> find(@Parameter(name = "id",
            example = "974ced59-46a6-4080-9025-597ea6cc4643",
            description = "Device id",
            required = true) UUID id);

    @Operation(summary = "Fetch a single device")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Device deleted"),
            @ApiResponse(responseCode = "404", description = "Device not found"),
            @ApiResponse(responseCode = "403", description = "Device cannot be deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error!")})
    ResponseEntity<Void> delete(@Parameter(name = "id",
            example = "974ced59-46a6-4080-9025-597ea6cc4643",
            description = "Device id",
            required = true) UUID id);

    @Operation(summary = "Update a device")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Device updated"),
            @ApiResponse(responseCode = "404", description = "Device not found"),
            @ApiResponse(responseCode = "403", description = "Device cannot be updated"),
            @ApiResponse(responseCode = "500", description = "Internal server error!")})
    ResponseEntity<Void> update(@Parameter(name = "id",
            example = "974ced59-46a6-4080-9025-597ea6cc4643",
            description = "Device id",
            required = true) UUID id,
                                @RequestBody(description = "Device body to update")
                                DeviceRequest deviceRequest);

    @Operation(summary = "Update device state")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Device state updated"),
            @ApiResponse(responseCode = "404", description = "Device not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error!")})
    ResponseEntity<Void> changeState(@Parameter(name = "id",
            example = "974ced59-46a6-4080-9025-597ea6cc4643",
            description = "Device id",
            required = true) UUID id,
                                     @Parameter(description = "Device state",
                                             example = "IN_USE",
                                             required = true)
                                     StateEnum stateEnum);


}
