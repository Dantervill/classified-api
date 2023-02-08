package com.senla.internship.classifiedapi.controller;

import com.senla.internship.classifiedapi.dto.request.LoginRequest;
import com.senla.internship.classifiedapi.dto.request.RegistrationRequest;
import com.senla.internship.classifiedapi.dto.response.TokenResponse;
import com.senla.internship.classifiedapi.service.high.level.AuthenticationService;
import com.senla.internship.classifiedapi.service.high.level.RegistrationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerTest {
    private static final String SIGNUP_URL = "/api/v1/accounts/signup";
    private static final String SIGNIN_URL = "/api/v1/accounts/signin";

    @MockBean
    private RegistrationService registrationService;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private BindingResult bindingResult;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldInitializeTestRestTemplate() {
        assertNotNull(restTemplate);
    }

    @Test
    void shouldSignUp() {
        RegistrationRequest registrationRequest = new RegistrationRequest("john@gmail.com", "Qwerty123%");
        TokenResponse tokenResponse = new TokenResponse("token", LocalDateTime.now(), LocalDateTime.from(LocalDateTime.now().plusMinutes(60)));

        given(registrationService.performRegistration(registrationRequest)).willReturn(tokenResponse);

        ResponseEntity<?> response = restTemplate.postForEntity(SIGNUP_URL, registrationRequest, TokenResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.equals(response.getBody(), tokenResponse));
    }

    @Test
    void shouldSignIn() {
        LoginRequest loginRequest = new LoginRequest("john@gmail.com", "Qwerty123%");
        TokenResponse tokenResponse = new TokenResponse("token", LocalDateTime.now(), LocalDateTime.from(LocalDateTime.now().plusMinutes(60)));

        given(authenticationService.performLogin(loginRequest)).willReturn(tokenResponse);

        ResponseEntity<?> response = restTemplate.postForEntity(SIGNIN_URL, loginRequest, TokenResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.equals(response.getBody(), tokenResponse));
    }
}