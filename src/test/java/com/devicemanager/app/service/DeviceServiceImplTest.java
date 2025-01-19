package com.devicemanager.app.service;

import com.devicemanager.app.dto.DeviceDTO;
import com.devicemanager.app.entity.DeviceEntity;
import com.devicemanager.app.enums.StateEnum;
import com.devicemanager.app.exception.DeviceInUseException;
import com.devicemanager.app.exception.DeviceNotFoundException;
import com.devicemanager.app.repository.DeviceRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeviceServiceImplTest {

    @InjectMocks
    private DeviceServiceImpl deviceService;

    @Mock
    private DeviceRepository deviceRepository;

    @Captor
    private ArgumentCaptor<DeviceEntity> deviceEntityArgumentCaptor;

    @Test
    void shouldCreate() {

        DeviceEntity deviceEntity = DeviceEntity.builder().id(UUID.randomUUID()).build();
        when(deviceRepository.save(any(DeviceEntity.class))).thenReturn(deviceEntity);

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

    @Test
    void shouldUpdateDevice() {

        DeviceEntity deviceEntity = DeviceEntity.builder()
                .id(UUID.randomUUID())
                .name(RandomStringUtils.secure().nextAlphanumeric(10))
                .brand(RandomStringUtils.secure().nextAlphanumeric(10))
                .build();
        when(deviceRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(deviceEntity));

        DeviceDTO deviceDTO = DeviceDTO.builder()
                .name("new-device-name")
                .brand("new-brand-name")
                .build();
        deviceService.update(UUID.randomUUID(), deviceDTO);

        verify(deviceRepository, times(1)).save(any(DeviceEntity.class));
        verify(deviceRepository).save(deviceEntityArgumentCaptor.capture());

        DeviceEntity deviceEntityCaught = deviceEntityArgumentCaptor.getValue();
        assertThat(deviceEntityCaught.getName(), equalTo(deviceDTO.name()));
        assertThat(deviceEntityCaught.getBrand(), equalTo(deviceDTO.brand()));

    }

    @Test
    void shouldNotUpdateDeviceBecauseDeviceNotFound() {

        when(deviceRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> {
            deviceService.update(UUID.randomUUID(), DeviceDTO.builder().build());
        });

        verify(deviceRepository, never()).save(any(DeviceEntity.class));

    }

    @Test
    void shouldNotUpdateDeviceBecauseDeviceIsInUse() {

        DeviceEntity deviceEntity = DeviceEntity.builder()
                .id(UUID.randomUUID())
                .name(RandomStringUtils.secure().nextAlphanumeric(10))
                .brand(RandomStringUtils.secure().nextAlphanumeric(10))
                .state(StateEnum.IN_USE)
                .build();
        when(deviceRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(deviceEntity));

        assertThrows(DeviceInUseException.class, () -> {
            deviceService.update(UUID.randomUUID(), DeviceDTO.builder().build());
        });

        verify(deviceRepository, never()).save(any(DeviceEntity.class));

    }

    @Test
    void shouldUpdateDeviceState() {

        DeviceEntity deviceEntity = DeviceEntity.builder()
                .id(UUID.randomUUID())
                .name(RandomStringUtils.secure().nextAlphanumeric(10))
                .brand(RandomStringUtils.secure().nextAlphanumeric(10))
                .state(StateEnum.AVAILABLE)
                .build();
        when(deviceRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(deviceEntity));

        DeviceDTO deviceDTO = DeviceDTO.builder()
                .stateEnum(StateEnum.INACTIVE)
                .build();
        deviceService.changeState(UUID.randomUUID(), deviceDTO);

        verify(deviceRepository, times(1)).save(any(DeviceEntity.class));
        verify(deviceRepository).save(deviceEntityArgumentCaptor.capture());

        DeviceEntity deviceEntityCaught = deviceEntityArgumentCaptor.getValue();
        assertThat(deviceEntityCaught.getState(), equalTo(deviceDTO.stateEnum()));

    }

    @Test
    void shouldNotUpdateDeviceStateBecauseDeviceNotFound() {

        when(deviceRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> {
            deviceService.changeState(UUID.randomUUID(), DeviceDTO.builder().build());
        });

        verify(deviceRepository, never()).save(any(DeviceEntity.class));

    }

}