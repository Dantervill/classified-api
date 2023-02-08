package com.senla.internship.classifiedapi.security.authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthenticationFacadeTest {
    @Mock private Authentication authentication;
    @Mock private SecurityContext securityContext;
    private AuthenticationFacade authenticationFacade;

    @BeforeEach
    void setUp() {
        this.authenticationFacade = new AuthenticationFacade();
    }

    @Test
    void shouldGetAuthentication() {
        given(securityContext.getAuthentication()).willReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        given(SecurityContextHolder.getContext().getAuthentication()).willReturn(authentication);

        authenticationFacade.getAuthentication();

        verify(securityContext).getAuthentication();
        verify(SecurityContextHolder.getContext()).getAuthentication();
    }
}