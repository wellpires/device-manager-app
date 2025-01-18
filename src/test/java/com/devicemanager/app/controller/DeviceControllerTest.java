package com.devicemanager.app.controller;

import com.devicemanager.app.dto.DeviceDTO;
import com.devicemanager.app.dto.request.DeviceRequest;
import com.devicemanager.app.exception.DeviceNotFoundException;
import com.devicemanager.app.service.DeviceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DeviceController.class)
@ExtendWith(SpringExtension.class)
class DeviceControllerTest {

    private static final String BASE_URL = "/devices";
    private static final String POST_CREATE_DEVICES = BASE_URL.concat("");
    private static final String GET_DEVICES = BASE_URL.concat("");
    private static final String GET_DEVICE = BASE_URL.concat("/{id}");

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DeviceService deviceService;

    @Test
    void shouldCreate() throws Exception {

        UUID deviceId = UUID.randomUUID();
        when(deviceService.create(any(DeviceDTO.class))).thenReturn(deviceId);

        DeviceDTO deviceDTO = DeviceDTO.builder()
                .brand("brand-test")
                .name("name-test")
                .build();

        DeviceRequest deviceRequest = DeviceRequest.builder()
                .deviceDTO(deviceDTO)
                .build();

        String jsonRequestBody = mapper.writeValueAsString(deviceRequest);

        mockMvc.perform(post(POST_CREATE_DEVICES).contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonRequestBody))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/devices/".concat(deviceId.toString())))
                .andDo(print());

    }

    @Test
    void shouldReturnAllDevices() throws Exception {

        List<DeviceDTO> deviceDTOs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DeviceDTO deviceDTO = DeviceDTO.builder()
                    .id(UUID.randomUUID())
                    .name(RandomStringUtils.secure().nextAlphanumeric(10))
                    .brand(RandomStringUtils.secure().nextAlphanumeric(10))
                    .build();
            deviceDTOs.add(deviceDTO);

        }

        PageImpl<DeviceDTO> deviceDTOPage = new PageImpl<>(deviceDTOs, Pageable.ofSize(10), 20);
        when(deviceService.list(any(Pageable.class))).thenReturn(deviceDTOPage);

        mockMvc.perform(get(GET_DEVICES).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(10))
                .andDo(print())
                .andReturn();
    }

    @Test
    void shouldFindOneDevice() throws Exception {

        DeviceDTO deviceDTO = DeviceDTO.builder()
                .id(UUID.randomUUID())
                .name(RandomStringUtils.secure().nextAlphanumeric(10))
                .brand(RandomStringUtils.secure().nextAlphanumeric(10))
                .build();

        when(deviceService.find(any(UUID.class))).thenReturn(deviceDTO);

        Map<String, UUID> param = new HashMap<>();
        param.put("id", UUID.randomUUID());

        URI getDeviceURI = UriComponentsBuilder.fromPath(GET_DEVICE)
                .buildAndExpand(param)
                .toUri();

        mockMvc.perform(get(getDeviceURI).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.device").isNotEmpty())
                .andExpect(jsonPath("$.device.id").value(deviceDTO.id().toString()))
                .andExpect(jsonPath("$.device.name").value(deviceDTO.name()))
                .andExpect(jsonPath("$.device.brand").value(deviceDTO.brand()))
                .andDo(print());

    }

    @Test
    void shouldNotFindOneDevice() throws Exception {

        when(deviceService.find(any(UUID.class))).thenThrow(new DeviceNotFoundException(UUID.randomUUID()));

        Map<String, UUID> param = new HashMap<>();
        param.put("id", UUID.randomUUID());

        URI getDeviceURI = UriComponentsBuilder.fromPath(GET_DEVICE)
                .buildAndExpand(param)
                .toUri();

        mockMvc.perform(get(getDeviceURI).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andDo(print());

    }
}