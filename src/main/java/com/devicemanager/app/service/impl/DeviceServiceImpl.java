package com.devicemanager.app.service.impl;

import com.devicemanager.app.dto.DeviceDTO;
import com.devicemanager.app.dto.DeviceStateDTO;
import com.devicemanager.app.dto.DeviceStateRequestDTO;
import com.devicemanager.app.entity.DeviceEntity;
import com.devicemanager.app.enums.StateEnum;
import com.devicemanager.app.exception.DeviceInUseException;
import com.devicemanager.app.exception.DeviceNotFoundException;
import com.devicemanager.app.mapper.DeviceEntity2DeviceDTOMapper;
import com.devicemanager.app.repository.DeviceRepository;
import com.devicemanager.app.service.DeviceService;
import com.devicemanager.app.service.DeviceStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceStateService deviceStateService;

    @Override
    public UUID create(DeviceDTO deviceDTO) {

        DeviceStateDTO deviceStateDTO = deviceStateService.findByName(StateEnum.AVAILABLE);

        DeviceEntity deviceEntity = DeviceEntity.builder()
                .name(deviceDTO.name())
                .brand(deviceDTO.brand())
                .stateId(deviceStateDTO.id())
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

        if(StateEnum.IN_USE.equals(deviceEntity.getDeviceStateEntity().getState())){
            throw new DeviceInUseException(String.format("Device %s cannot be deleted because it is currently in use.", deviceEntity.getName()));
        }

        deviceRepository.delete(deviceEntity);
    }

    @Override
    public void update(UUID id, DeviceDTO deviceDTO) {

        DeviceEntity deviceEntity = deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(id));

        if(StateEnum.IN_USE.equals(deviceEntity.getDeviceStateEntity().getState())){
            throw new DeviceInUseException(String.format("Device %s cannot be modified because it is currently in use.", deviceEntity.getName()));
        }

        deviceEntity.setName(deviceDTO.name());
        deviceEntity.setBrand(deviceDTO.brand());

        deviceRepository.save(deviceEntity);

    }

    @Override
    public void changeState(UUID id, StateEnum stateEnum) {

        DeviceEntity deviceEntity = deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(id));

        DeviceStateDTO deviceStateDTO = deviceStateService.findByName(stateEnum);

        if(StateEnum.IN_USE.equals(deviceEntity.getDeviceStateEntity().getState())){
            throw new DeviceInUseException(String.format("In Use Devices %s cannot be modified without activation request", deviceEntity.getName()));
        }

        deviceEntity.setStateId(deviceStateDTO.id());

        deviceRepository.save(deviceEntity);

    }

}
