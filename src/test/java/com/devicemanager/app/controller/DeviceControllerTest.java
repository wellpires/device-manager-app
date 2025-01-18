package com.devicemanager.app.controller;

import com.devicemanager.app.dto.DeviceDTO;
import com.devicemanager.app.dto.request.DeviceRequest;
import com.devicemanager.app.service.DeviceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeviceController.class)
@ExtendWith(SpringExtension.class)
class DeviceControllerTest {

    private static final String BASE_URL = "/devices";
    private static final String POST_CREATE_DEVICES = BASE_URL.concat("");

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
}