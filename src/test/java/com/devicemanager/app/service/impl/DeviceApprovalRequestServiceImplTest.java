package com.devicemanager.app.service.impl;

import com.devicemanager.app.dto.DeviceDTO;
import com.devicemanager.app.dto.DeviceStateDTO;
import com.devicemanager.app.dto.DeviceStateRequestDTO;
import com.devicemanager.app.entity.DeviceApprovalRequestEntity;
import com.devicemanager.app.entity.DeviceEntity;
import com.devicemanager.app.entity.DeviceStateEntity;
import com.devicemanager.app.enums.StateEnum;
import com.devicemanager.app.repository.DeviceApprovalRequestRepository;
import com.devicemanager.app.service.DeviceService;
import com.devicemanager.app.service.DeviceStateService;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeviceApprovalRequestServiceImplTest {

    @InjectMocks
    private DeviceApprovalRequestServiceImpl deviceApprovalRequestService;

    @Mock
    private DeviceApprovalRequestRepository deviceApprovalRequestRepository;

    @Mock
    private DeviceService deviceService;

    @Mock
    private DeviceStateService deviceStateService;

    @Test
    void shouldListStatesRequests() {

        List<DeviceApprovalRequestEntity> deviceApprovalRequestEntities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DeviceEntity deviceEntity = DeviceEntity.builder()
                    .name(RandomStringUtils.secure().nextAlphanumeric(10))
                    .build();

            DeviceStateEntity deviceStateEntity = DeviceStateEntity.builder()
                    .state(StateEnum.AVAILABLE)
                    .build();

            DeviceApprovalRequestEntity deviceApprovalRequestEntity = DeviceApprovalRequestEntity.builder()
                    .id(UUID.randomUUID())
                    .deviceEntity(deviceEntity)
                    .deviceStateEntity(deviceStateEntity)
                    .build();
            deviceApprovalRequestEntities.add(deviceApprovalRequestEntity);
        }

        when(deviceApprovalRequestRepository.findByDeviceId(any(UUID.class))).thenReturn(deviceApprovalRequestEntities);

        List<DeviceStateRequestDTO> deviceStateRequestDTOs = deviceApprovalRequestService.listStatesRequests(UUID.randomUUID());

        assertThat(deviceStateRequestDTOs, hasSize(10));

    }

    @Test
    void shouldApproveRequest() {

        DeviceApprovalRequestEntity deviceApprovalRequestEntity = DeviceApprovalRequestEntity.builder()
                .deviceId(UUID.randomUUID())
                .stateId(UUID.randomUUID())
                .build();
        when(deviceApprovalRequestRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(deviceApprovalRequestEntity));

        deviceApprovalRequestService.approveRequest(UUID.randomUUID());

        verify(deviceService, times(1)).changeState(any(UUID.class), any(UUID.class));
        verify(deviceApprovalRequestRepository, times(1)).delete(any(DeviceApprovalRequestEntity.class));

    }

    @Test
    void shouldCreate(){

        DeviceDTO deviceDTO = DeviceDTO.builder().id(UUID.randomUUID()).build();
        when(deviceService.find(any(UUID.class))).thenReturn(deviceDTO);

        DeviceStateDTO deviceStateDTOFound = DeviceStateDTO.builder().build();
        when(deviceStateService.findByName(any(StateEnum.class))).thenReturn(deviceStateDTOFound);

        DeviceStateDTO deviceStateDTO = DeviceStateDTO.builder().name(StateEnum.AVAILABLE).build();
        deviceApprovalRequestService.create(UUID.randomUUID(), deviceStateDTO);

        verify(deviceApprovalRequestRepository, times(1)).save(any(DeviceApprovalRequestEntity.class));

    }
}