package com.devicemanager.app.service.impl;

import com.devicemanager.app.dto.DeviceStateDTO;
import com.devicemanager.app.entity.DeviceStateEntity;
import com.devicemanager.app.enums.StateEnum;
import com.devicemanager.app.exception.DeviceStateNotFoundException;
import com.devicemanager.app.repository.DeviceStateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeviceStateServiceImplTest {

    @InjectMocks
    private DeviceStateServiceImpl deviceStateService;

    @Mock
    private DeviceStateRepository deviceStateRepository;

    @Test
    void shouldFindByName() {

        DeviceStateEntity deviceStateEntity = DeviceStateEntity.builder()
                .id(UUID.randomUUID())
                .state(StateEnum.AVAILABLE)
                .build();

        when(deviceStateRepository.findByState(any(StateEnum.class))).thenReturn(Optional.ofNullable(deviceStateEntity));

        DeviceStateDTO deviceStateDTO = deviceStateService.findByName(StateEnum.AVAILABLE);

        assertThat(deviceStateDTO, is(notNullValue()));
        assertThat(deviceStateDTO.id(), equalTo(deviceStateEntity.getId()));
        assertThat(deviceStateDTO.name(), equalTo(deviceStateEntity.getState()));

    }

    @Test
    void shouldNotFindByName() {

        when(deviceStateRepository.findByState(any(StateEnum.class))).thenReturn(Optional.empty());

        assertThrows(DeviceStateNotFoundException.class, () -> {
            deviceStateService.findByName(StateEnum.AVAILABLE);
        });

    }

    @Test
    void shouldFindById() {

        DeviceStateEntity deviceStateEntity = DeviceStateEntity.builder()
                .id(UUID.randomUUID())
                .state(StateEnum.AVAILABLE)
                .build();
        when(deviceStateRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(deviceStateEntity));

        DeviceStateDTO deviceStateDTO = deviceStateService.findById(UUID.randomUUID());

        assertThat(deviceStateDTO, is(notNullValue()));
        assertThat(deviceStateDTO.id(), equalTo(deviceStateEntity.getId()));
        assertThat(deviceStateDTO.name(), equalTo(deviceStateEntity.getState()));

    }

    @Test
    void shouldNotFindByIdBecauseNotFound() {

        when(deviceStateRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(DeviceStateNotFoundException.class, () -> {
            deviceStateService.findById(UUID.randomUUID());
        });


    }
}