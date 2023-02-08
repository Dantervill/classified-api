package com.senla.internship.classifiedapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.senla.internship.classifiedapi.dto.request.ProfileRequest;
import com.senla.internship.classifiedapi.security.filter.JwtFilter;
import com.senla.internship.classifiedapi.service.high.level.ProfileManagementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserProfileController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileManagementService profileManagementService;

    @MockBean
    private JwtFilter filter;

    @Test
    void shouldInitializeMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    void shouldEditProfile() throws Exception {
        ProfileRequest profileRequest = new ProfileRequest("Test", "+79458905438");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(profileRequest);

        mockMvc.perform(put("/api/v1/profile/management/editing")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse();
    }
}