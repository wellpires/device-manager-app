package com.devicemanager.app.service;

import com.devicemanager.app.dto.DeviceDTO;
import com.devicemanager.app.entity.DeviceEntity;
import com.devicemanager.app.enums.StateEnum;
import com.devicemanager.app.exception.DeviceNotFoundException;
import com.devicemanager.app.mapper.DeviceEntity2DeviceDTOMapper;
import com.devicemanager.app.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<DeviceDTO> list(Pageable pageable) {
        return deviceRepository.findAll(pageable)
                .map(new DeviceEntity2DeviceDTOMapper());
    }

    @Override
    public DeviceDTO find(UUID id) {
        return deviceRepository.findById(id)
                .map(new DeviceEntity2DeviceDTOMapper())
                .orElseThrow(() -> new DeviceNotFoundException(id));
    }
}
