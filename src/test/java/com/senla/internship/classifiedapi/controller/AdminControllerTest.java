package com.senla.internship.classifiedapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.senla.internship.classifiedapi.dto.response.DashboardResponse;
import com.senla.internship.classifiedapi.model.account.User;
import com.senla.internship.classifiedapi.security.filter.JwtFilter;
import com.senla.internship.classifiedapi.service.low.level.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtFilter filter;

    @Test
    @WithMockUser(username = "john@gmail.com", roles = {"ADMIN"})
    void shouldGetAdminDashboard() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        List<User> users = Collections.singletonList(new User());

        given(userService.findAll()).willReturn(users);

        DashboardResponse dashboardResponse = new DashboardResponse(users.size());
        String responseJson = ow.writeValueAsString(dashboardResponse);

        mockMvc.perform(get("/api/v1/admin/dashboard"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responseJson))
                .andExpect(status().isOk());
    }
}