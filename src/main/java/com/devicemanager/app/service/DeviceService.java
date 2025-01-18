package com.devicemanager.app.service;

import com.devicemanager.app.dto.DeviceDTO;

import java.util.UUID;

public interface DeviceService {

    UUID create(DeviceDTO deviceDTO);

}
