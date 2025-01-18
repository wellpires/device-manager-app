package com.devicemanager.app.service;

import com.devicemanager.app.dto.DeviceDTO;
import com.devicemanager.app.entity.DeviceEntity;
import com.devicemanager.app.enums.StateEnum;
import com.devicemanager.app.exception.DeviceInUseException;
import com.devicemanager.app.exception.DeviceNotFoundException;
import com.devicemanager.app.repository.DeviceRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeviceServiceImplTest {

    @InjectMocks
    private DeviceServiceImpl deviceService;

    @Mock
    private DeviceRepository deviceRepository;

    @Test
    void shouldCreate() {

        DeviceDTO deviceDTO = DeviceDTO.builder()
                .name("name-test")
                .brand("brand-test")
                .build();

        deviceService.create(deviceDTO);

    }

    @Test
    void shouldList() {

        List<DeviceEntity> deviceEntities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DeviceEntity deviceEntity = DeviceEntity.builder()
                    .id(UUID.randomUUID())
                    .name(RandomStringUtils.secure().nextAlphanumeric(10))
                    .brand(RandomStringUtils.secure().nextAlphanumeric(10))
                    .build();
            deviceEntities.add(deviceEntity);

        }

        PageImpl<DeviceEntity> deviceDTOPage = new PageImpl<>(deviceEntities, Pageable.ofSize(10), 10);

        when(deviceRepository.findAll(any(Pageable.class))).thenReturn(deviceDTOPage);

        Page<DeviceDTO> pageDeviceDTOs = deviceService.list(Pageable.ofSize(10));

        assertThat(pageDeviceDTOs.getTotalElements(), equalTo(10L));

    }

    @Test
    void shouldFindOneDevice() {

        DeviceEntity deviceEntity = DeviceEntity.builder()
                .id(UUID.randomUUID())
                .name(RandomStringUtils.secure().nextAlphanumeric(10))
                .brand(RandomStringUtils.secure().nextAlphanumeric(10))
                .build();
        when(deviceRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(deviceEntity));

        DeviceDTO deviceDTO = deviceService.find(UUID.randomUUID());

        assertThat(deviceDTO, notNullValue());
        assertThat(deviceDTO.id(), equalTo(deviceEntity.getId()));
        assertThat(deviceDTO.name(), equalTo(deviceEntity.getName()));
        assertThat(deviceDTO.brand(), equalTo(deviceEntity.getBrand()));

    }

    @Test
    void shouldNotFindOneDevice() {

        when(deviceRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> {
            deviceService.find(UUID.randomUUID());
        });
    }

    @Test
    void shouldDeleteDevice() {

        DeviceEntity deviceEntity = DeviceEntity.builder()
                .id(UUID.randomUUID())
                .name(RandomStringUtils.secure().nextAlphanumeric(10))
                .brand(RandomStringUtils.secure().nextAlphanumeric(10))
                .build();
        when(deviceRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(deviceEntity));

        deviceService.delete(UUID.randomUUID());

        verify(deviceRepository, times(1)).delete(any(DeviceEntity.class));

    }

    @Test
    void shouldNotDeleteDeviceBecauseDeviceNotFound() {

        when(deviceRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> {
            deviceService.delete(UUID.randomUUID());
        });

        verify(deviceRepository, never()).delete(any(DeviceEntity.class));

    }

    @Test
    void shouldNotDeleteDeviceBecauseDeviceIsInUse() {

        DeviceEntity deviceEntity = DeviceEntity.builder()
                .id(UUID.randomUUID())
                .name(RandomStringUtils.secure().nextAlphanumeric(10))
                .brand(RandomStringUtils.secure().nextAlphanumeric(10))
                .state(StateEnum.IN_USE)
                .build();
        when(deviceRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(deviceEntity));

        assertThrows(DeviceInUseException.class, () -> {
            deviceService.delete(UUID.randomUUID());
        });

        verify(deviceRepository, never()).delete(any(DeviceEntity.class));

    }

}