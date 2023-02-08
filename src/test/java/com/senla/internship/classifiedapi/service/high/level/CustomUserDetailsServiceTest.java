package com.senla.internship.classifiedapi.service.high.level;

import com.senla.internship.classifiedapi.model.account.User;
import com.senla.internship.classifiedapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {
    @Mock private UserRepository userRepository;
    private CustomUserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        this.userDetailsService = new CustomUserDetailsService(userRepository);
    }

    @Test
    void shouldLoadUserByUsernameWhenExists() {
        String email = "johndoe@gmail.com";

        given(userRepository.findByEmail(email)).willReturn(Optional.of(new User()));

        userDetailsService.loadUserByUsername(email);

        verify(userRepository).findByEmail(email);
    }

    @Test
    void shouldThrowExceptionWhenUsernameNotExists() {
        String email = "johndoe@gmail.com";
        String message = String.format("User with email %s is not found!", email);

        assertThatThrownBy(() -> userDetailsService.loadUserByUsername(email))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage(message);
    }
}