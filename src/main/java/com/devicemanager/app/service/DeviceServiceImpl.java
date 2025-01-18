package com.devicemanager.app.service;

import com.devicemanager.app.dto.DeviceDTO;
import com.devicemanager.app.entity.DeviceEntity;
import com.devicemanager.app.enums.StateEnum;
import com.devicemanager.app.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    @Override
    public UUID create(DeviceDTO deviceDTO) {

        DeviceEntity deviceEntity = DeviceEntity.builder()
                .name(deviceDTO.name())
                .brand(deviceDTO.brand())
                .state(StateEnum.AVAILABLE)
                .build();

        return deviceRepository.save(deviceEntity).getId();

    }
}
