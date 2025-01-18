package com.devicemanager.app.controller;

import com.devicemanager.app.controller.resource.DeviceResource;
import com.devicemanager.app.dto.DeviceDTO;
import com.devicemanager.app.dto.request.DeviceRequest;
import com.devicemanager.app.dto.response.DeviceResponse;
import com.devicemanager.app.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("devices")
@RequiredArgsConstructor
public class DeviceController implements DeviceResource {

    private final DeviceService deviceService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<Void> create(@RequestBody DeviceRequest deviceRequest) {

        UUID id = deviceService.create(deviceRequest.deviceDTO());

        Map<String, UUID> param = new HashMap<>();
        param.put("id", id);

        URI deviceCreateURI = UriComponentsBuilder
                .fromUriString("/devices/{id}")
                .buildAndExpand(param)
                .toUri();

        return ResponseEntity.created(deviceCreateURI).build();
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<DeviceDTO> list(Pageable pageable) {
        return deviceService.list(pageable);
    }

    @Override
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeviceResponse> find(@PathVariable("id") UUID id) {

        DeviceDTO deviceDTO = deviceService.find(id);

        DeviceResponse deviceResponse = DeviceResponse.builder()
                .deviceDTO(deviceDTO)
                .build();
        return ResponseEntity.ok(deviceResponse);
    }

    @Override
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {

        deviceService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@PathVariable("id") UUID id, @RequestBody DeviceRequest deviceRequest) {

        deviceService.update(id, deviceRequest.deviceDTO());

        return ResponseEntity.noContent().build();
    }

}
