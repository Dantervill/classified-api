package com.senla.internship.classifiedapi.service.high.level;

import com.senla.internship.classifiedapi.dto.request.LoginRequest;
import com.senla.internship.classifiedapi.security.jwt.JwtHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    private LoginRequest loginRequest;
    @Mock private JwtHandler jwtHandler;
    @Mock private DaoAuthenticationProvider authenticationProvider;
    @Mock private Authentication authentication;
    private AuthenticationService authenticationService;
    private UsernamePasswordAuthenticationToken authToken;

    @BeforeEach
    void setUp() {
        this.authenticationService =
                new AuthenticationService(jwtHandler, authenticationProvider);
        this.loginRequest = new LoginRequest("johndoe@gmail.com", "qwerty123%");
        this.authToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
    }

    @Test
    void shouldPerformLoginWhenAuthTokenIsCorrect() {
        // подготовка
        given(authenticationProvider.authenticate(authToken)).willReturn(authentication);
        given(jwtHandler.generateToken(loginRequest.getEmail())).willReturn("token");

        // отправка
        authenticationService.performLogin(loginRequest);

        // проверка
        verify(authenticationProvider).authenticate(authToken);
        verify(jwtHandler).generateToken(loginRequest.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenAuthTokenIsIncorrect() {
        given(authenticationProvider.authenticate(authToken))
                .willThrow(BadCredentialsException.class);

        assertThatThrownBy(() -> authenticationProvider.authenticate(authToken))
                .isInstanceOf(BadCredentialsException.class);
    }

}