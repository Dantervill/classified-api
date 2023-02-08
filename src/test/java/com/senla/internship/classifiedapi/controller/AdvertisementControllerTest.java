package com.senla.internship.classifiedapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.senla.internship.classifiedapi.dto.request.AdvertisementRequest;
import com.senla.internship.classifiedapi.dto.request.LocationRequest;
import com.senla.internship.classifiedapi.model.advertisement.Advertisement;
import com.senla.internship.classifiedapi.security.filter.JwtFilter;
import com.senla.internship.classifiedapi.service.high.level.AdvertisementManagementService;
import com.senla.internship.classifiedapi.service.high.level.AdvertisementSearchingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AdvertisementController.class)
class AdvertisementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdvertisementSearchingService advertisementSearchingService;

    @MockBean
    private AdvertisementManagementService advertisementManagementService;

    @MockBean
    private JwtFilter filter;

    @Test
    void shouldInitializeMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    void shouldSearchAdsContainingTitle() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        List<Advertisement> advertisements = Collections.emptyList();
        String responseJson = ow.writeValueAsString(advertisements);
        String title = "Smth";

        mockMvc.perform(get("/api/v1/advertisements")
                .param("title", title))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responseJson))
                .andReturn().getResponse();

    }

    @Test
    void shouldGetAdsContainingTitleAndFilteredByPrice() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        List<Advertisement> advertisements = Collections.emptyList();
        String responseJson = ow.writeValueAsString(advertisements);
        String title = "Smth";
        BigDecimal min = new BigDecimal(1000);
        BigDecimal max = new BigDecimal(2000);

        mockMvc.perform(get("/api/v1/advertisements")
                        .param("title", title)
                        .param("min", String.valueOf(min))
                        .param("max", String.valueOf(max)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responseJson))
                .andReturn().getResponse();
    }

    @Test
    void shouldCreateAdvertisement() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        LocationRequest locationRequest = new LocationRequest("Moscow", "Moscovskaya Oblast'");
        AdvertisementRequest advertisementRequest = new AdvertisementRequest("ELECTRONICS", "Title",
                "SECOND_HAND", "APPLE", "Description",
                new BigDecimal(10000), locationRequest);
        String requestJson = ow.writeValueAsString(advertisementRequest);

        mockMvc.perform(post("/api/v1/profile/advertisements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse();
    }

    @Test
    void shouldDeleteAdvertisement() throws Exception {
        mockMvc.perform(delete("/api/v1/profile/advertisements/active-advertisements/{id}", 1))
                .andExpect(status().isOk())
                .andReturn().getResponse();
    }

    @Test
    void shouldUpdateAdvertisement() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        LocationRequest locationRequest = new LocationRequest("Moscow", "Moscovskaya Oblast'");
        AdvertisementRequest advertisementRequest = new AdvertisementRequest("ELECTRONICS", "Title",
                "SECOND_HAND", "APPLE", "Description",
                new BigDecimal(10000), locationRequest);
        String requestJson = ow.writeValueAsString(advertisementRequest);

        mockMvc.perform(put("/api/v1/profile/advertisements/active-advertisements/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse();
    }

    @Test
    void shouldMakeAdvertisementVIP() throws Exception {
        mockMvc.perform(put("/api/v1/profile/advertisements/active-advertisements/{advertisementId}/status", 1))
                .andExpect(status().isOk())
                .andReturn().getResponse();
    }

    @Test
    void shouldGetMySoldAdvertisements() throws Exception {
        mockMvc.perform(get("/api/v1/profile/advertisements/sold-advertisements"))
                .andExpect(status().isOk())
                .andReturn().getResponse();
    }

    @Test
    void shouldGetUserSoldAdvertisements() throws Exception {
        mockMvc.perform(get("/api/v1/users/{id}/profile/advertisements/sold-advertisements", 1))
                .andExpect(status().isOk())
                .andReturn().getResponse();
    }
}