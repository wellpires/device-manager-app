package com.devicemanager.app.service.impl;

import com.devicemanager.app.dto.DeviceStateRequestDTO;
import com.devicemanager.app.entity.DeviceApprovalRequestEntity;
import com.devicemanager.app.entity.DeviceEntity;
import com.devicemanager.app.entity.DeviceStateEntity;
import com.devicemanager.app.enums.StateEnum;
import com.devicemanager.app.repository.DeviceApprovalRequestRepository;
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
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeviceApprovalRequestServiceImplTest {

    @InjectMocks
    private DeviceApprovalRequestServiceImpl deviceApprovalRequestService;

    @Mock
    private DeviceApprovalRequestRepository deviceApprovalRequestRepository;

    @Test
    void shouldListStatesRequests() {

        List<DeviceApprovalRequestEntity> deviceApprovalRequestEntities = new ArrayList<>();
        for(int i = 0; i < 10; i++){
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
}