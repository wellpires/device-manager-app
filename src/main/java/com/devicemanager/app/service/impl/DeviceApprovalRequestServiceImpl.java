package com.devicemanager.app.service.impl;

import com.devicemanager.app.dto.DeviceStateRequestDTO;
import com.devicemanager.app.entity.DeviceApprovalRequestEntity;
import com.devicemanager.app.mapper.DeviceApprovalRequestEntity2DeviceStateRequestDTOMapper;
import com.devicemanager.app.repository.DeviceApprovalRequestRepository;
import com.devicemanager.app.service.DeviceApprovalRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceApprovalRequestServiceImpl implements DeviceApprovalRequestService {

    private final DeviceApprovalRequestRepository deviceApprovalRequestRepository;

    @Override
    public List<DeviceStateRequestDTO> listStatesRequests(UUID deviceId) {
        return deviceApprovalRequestRepository.findByDeviceId(deviceId).stream()
                .map(new DeviceApprovalRequestEntity2DeviceStateRequestDTOMapper())
                .toList();
    }
}
