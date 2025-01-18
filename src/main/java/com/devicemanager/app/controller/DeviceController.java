package com.devicemanager.app.controller;

import com.devicemanager.app.controller.resource.DeviceResource;
import com.devicemanager.app.dto.request.DeviceRequest;
import com.devicemanager.app.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
