package com.senla.internship.classifiedapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.senla.internship.classifiedapi.dto.request.CommentRequest;
import com.senla.internship.classifiedapi.security.filter.JwtFilter;
import com.senla.internship.classifiedapi.service.high.level.CommentManagementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentManagementService commentManagementService;

    @MockBean
    private JwtFilter filter;

    @Test
    void shouldInitializeMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    void shouldPostComment() throws Exception {
        CommentRequest commentRequest = new CommentRequest("Header", "Body");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(commentRequest);

        mockMvc.perform(post("/api/v1/users/{id}/profile/advertisements/active-advertisements/{advertisementId}/comments", 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse();
    }
}