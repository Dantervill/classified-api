package com.senla.internship.classifiedapi.service.low.level;

import com.senla.internship.classifiedapi.exception.DataNotFoundException;
import com.senla.internship.classifiedapi.exception.UserNotFoundException;
import com.senla.internship.classifiedapi.model.account.Profile;
import com.senla.internship.classifiedapi.model.account.Role;
import com.senla.internship.classifiedapi.model.account.User;
import com.senla.internship.classifiedapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock private UserRepository userRepository;
    private IUserService userService;
    private User user;

    @BeforeEach
    void setUp() {
        this.userService = new UserService(userRepository);
        Profile profile = Profile.builder()
                .id(1L)
                .name("")
                .phone("")
                .balance(new BigDecimal(150))
                .rating(5.0)
                .messages(Collections.emptyList())
                .advertisements(Collections.emptyList())
                .build();
        Role role = new Role(1L, "USER");
        this.user = new User(1L, "johndoe@gail.com", "Qwerty123%", role, profile);
    }

    @Test
    void shouldFindByEmailWhenNotNull() {
        given(userRepository.findByEmail(user.getEmail()))
                .willReturn(Optional.ofNullable(user));

        userService.findByEmail(user.getEmail());

        verify(userRepository).findByEmail(user.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenFindByEmailIsEqualToNull() {
        String message = String.format("User with email %s is not found!", user.getEmail());

        assertThatThrownBy(() -> userService.findByEmail(user.getEmail()))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage(message);
    }

    @Test
    void shouldFindByIdWhenNotNull() {
        given(userRepository.findById(user.getId()))
                .willReturn(Optional.ofNullable(user));

        userService.findById(user.getId());

        verify(userRepository).findById(user.getId());
    }

    @Test
    void shouldThrowExceptionWhenFindByIdIsEqualToNull() {
        String message = String.format("User with ID %d is not found!", user.getId());

        assertThatThrownBy(() -> userService.findById(user.getId()))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage(message);
    }

    @Test
    void shouldReturnTrueWhenExistsByEmail() {
        given(userRepository.existsByEmail(user.getEmail()))
                .willReturn(true);

        userService.existsByEmail(user.getEmail());

        verify(userRepository).existsByEmail(user.getEmail());
    }

    @Test
    void shouldReturnFalseWhenNotExistsByEmail() {
        given(userRepository.existsByEmail(user.getEmail()))
                .willReturn(false);

        userService.existsByEmail(user.getEmail());

        verify(userRepository).existsByEmail(user.getEmail());
    }

    @Test
    void shouldReturnTrueWhenExistsById() {
        given(userRepository.existsById(user.getId()))
                .willReturn(true);

        userService.existsById(user.getId());

        verify(userRepository).existsById(user.getId());
    }

    @Test
    void shouldReturnFalseWhenNotExistsById() {
        given(userRepository.existsById(user.getId()))
                .willReturn(false);

        userService.existsById(user.getId());

        verify(userRepository).existsById(user.getId());
    }

    @Test
    void shouldSaveWhenNotExistsByEmail() {
        userService.save(user);

        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    void shouldThrowExceptionWhenFindAll() {
        String message = "Data not found";

        assertThatThrownBy(() -> userService.findAll())
                .isInstanceOf(DataNotFoundException.class)
                .hasMessage(message);
    }

    @Test
    void shouldFindAllWhenListNotEmpty() {
        given(userRepository.findAll())
                .willReturn(Collections.singletonList(user));

        userService.findAll();

        verify(userRepository).findAll();
    }
}