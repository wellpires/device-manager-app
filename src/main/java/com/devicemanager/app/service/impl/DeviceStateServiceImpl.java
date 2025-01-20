package com.devicemanager.app.service.impl;

import com.devicemanager.app.dto.DeviceStateDTO;
import com.devicemanager.app.enums.StateEnum;
import com.devicemanager.app.exception.DeviceStateNotFoundException;
import com.devicemanager.app.mapper.DeviceStateEntity2DeviceStateDTOMapper;
import com.devicemanager.app.repository.DeviceStateRepository;
import com.devicemanager.app.service.DeviceStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceStateServiceImpl implements DeviceStateService {

    private final DeviceStateRepository deviceStateRepository;

    @Override
    public DeviceStateDTO findByName(StateEnum stateEnum) {

        return deviceStateRepository.findByState(stateEnum)
                .map(new DeviceStateEntity2DeviceStateDTOMapper())
                .orElseThrow(() -> new DeviceStateNotFoundException(stateEnum));
    }
}
