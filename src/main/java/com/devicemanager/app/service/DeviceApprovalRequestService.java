package com.devicemanager.app.service;

import com.devicemanager.app.dto.DeviceStateDTO;
import com.devicemanager.app.dto.DeviceStateRequestDTO;

import java.util.List;
import java.util.UUID;

public interface DeviceApprovalRequestService {
    List<DeviceStateRequestDTO> listStatesRequests(UUID deviceId);

    void approveRequest(UUID id);

    void create(UUID deviceId, DeviceStateDTO deviceStateDTO);
}
