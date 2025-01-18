package com.devicemanager.app.service;

import com.devicemanager.app.dto.DeviceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DeviceService {

    UUID create(DeviceDTO deviceDTO);

    Page<DeviceDTO> list(Pageable pageable);
}
