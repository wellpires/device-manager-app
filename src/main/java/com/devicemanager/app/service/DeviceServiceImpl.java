package com.devicemanager.app.service;

import com.devicemanager.app.dto.DeviceDTO;
import com.devicemanager.app.entity.DeviceEntity;
import com.devicemanager.app.enums.StateEnum;
import com.devicemanager.app.exception.DeviceInUseException;
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

    @Override
    public void delete(UUID id) {
        DeviceEntity deviceEntity = deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(id));

        if(StateEnum.IN_USE.equals(deviceEntity.getState())){
            throw new DeviceInUseException(String.format("Device %s cannot be deleted because it is currently in use.", deviceEntity.getName()));
        }

        deviceRepository.delete(deviceEntity);
    }

    @Override
    public void update(UUID id, DeviceDTO deviceDTO) {

        DeviceEntity deviceEntity = deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(id));

        if(StateEnum.IN_USE.equals(deviceEntity.getState())){
            throw new DeviceInUseException(String.format("Device %s cannot be modified because it is currently in use.", deviceEntity.getName()));
        }

        deviceEntity.setName(deviceDTO.name());
        deviceEntity.setBrand(deviceDTO.brand());

        deviceRepository.save(deviceEntity);

    }
}
