package com.devicemanager.app.service.impl;

import com.devicemanager.app.dto.DeviceDTO;
import com.devicemanager.app.dto.DeviceStateDTO;
import com.devicemanager.app.dto.DeviceStateRequestDTO;
import com.devicemanager.app.entity.DeviceApprovalRequestEntity;
import com.devicemanager.app.exception.DeviceStateRequestNotFound;
import com.devicemanager.app.mapper.DeviceApprovalRequestEntity2DeviceStateRequestDTOMapper;
import com.devicemanager.app.repository.DeviceApprovalRequestRepository;
import com.devicemanager.app.service.DeviceApprovalRequestService;
import com.devicemanager.app.service.DeviceService;
import com.devicemanager.app.service.DeviceStateService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceApprovalRequestServiceImpl implements DeviceApprovalRequestService {

    private final DeviceApprovalRequestRepository deviceApprovalRequestRepository;
    private final DeviceService deviceService;
    private final DeviceStateService deviceStateService;

    @Override
    public List<DeviceStateRequestDTO> listStatesRequests(UUID deviceId) {
        return deviceApprovalRequestRepository.findByDeviceId(deviceId).stream()
                .map(new DeviceApprovalRequestEntity2DeviceStateRequestDTOMapper())
                .toList();
    }

    @Override
    @Transactional
    public void approveRequest(UUID id) {

        DeviceApprovalRequestEntity deviceApprovalRequestEntity = deviceApprovalRequestRepository.findById(id)
                .orElseThrow(() -> new DeviceStateRequestNotFound(id));

        deviceService.changeState(deviceApprovalRequestEntity.getDeviceId(), deviceApprovalRequestEntity.getStateId());

        deviceApprovalRequestRepository.delete(deviceApprovalRequestEntity);

    }

    @Override
    public void create(UUID deviceId, DeviceStateDTO deviceStateDTO) {

        DeviceDTO deviceDTO = deviceService.find(deviceId);

        DeviceStateDTO deviceStateDTOFound = deviceStateService.findByName(deviceStateDTO.name());

        DeviceApprovalRequestEntity deviceApprovalRequestEntity = DeviceApprovalRequestEntity.builder()
                .deviceId(deviceDTO.id())
                .stateId(deviceStateDTOFound.id()).build();

        deviceApprovalRequestRepository.save(deviceApprovalRequestEntity);

    }
}
