package com.devicemanager.app.service;

import com.devicemanager.app.dto.DeviceDTO;
import com.devicemanager.app.entity.DeviceEntity;
import com.devicemanager.app.repository.DeviceRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
}