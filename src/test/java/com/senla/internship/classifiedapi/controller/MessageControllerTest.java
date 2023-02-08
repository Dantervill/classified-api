package com.senla.internship.classifiedapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.senla.internship.classifiedapi.dto.request.MessageRequest;
import com.senla.internship.classifiedapi.model.account.Message;
import com.senla.internship.classifiedapi.security.filter.JwtFilter;
import com.senla.internship.classifiedapi.service.high.level.MessageManagementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(MessageController.class)
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageManagementService messageManagementService;

    @MockBean
    private JwtFilter filter;

    @Test
    void shouldInitializeMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    void sendMessage() throws Exception {
        MessageRequest messageRequest = new MessageRequest("Text");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(messageRequest);

        mockMvc.perform(post("/api/v1/users/{id}/profile/messages", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse();
    }

    @Test
    void readMessages() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        List<Message> messages = Collections.emptyList();
        String responseJson = ow.writeValueAsString(messages);

        mockMvc.perform(get("/api/v1/profile/messages"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responseJson))
                .andExpect(status().isOk());
    }

    @Test
    void readMessage() throws Exception {
        mockMvc.perform(get("/api/v1/profile/messages/{id}", 1))
                .andExpect(status().isOk())
                .andReturn().getResponse();
    }
}