package com.senla.internship.classifiedapi.service.high.level;

import com.senla.internship.classifiedapi.dto.request.RegistrationRequest;
import com.senla.internship.classifiedapi.exception.RegistrationFailedException;
import com.senla.internship.classifiedapi.exception.RoleNotFoundException;
import com.senla.internship.classifiedapi.model.account.Profile;
import com.senla.internship.classifiedapi.model.account.Role;
import com.senla.internship.classifiedapi.model.account.User;
import com.senla.internship.classifiedapi.security.jwt.JwtHandler;
import com.senla.internship.classifiedapi.service.low.level.ProfileService;
import com.senla.internship.classifiedapi.service.low.level.RoleService;
import com.senla.internship.classifiedapi.service.low.level.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {
    @Mock private ModelMapper mapper;
    @Mock private JwtHandler jwtHandler;
    @Mock private UserService userService;
    @Mock private RoleService roleService;
    @Mock private ProfileService profileService;
    @Mock private BCryptPasswordEncoder passwordEncoder;
    private RegistrationService registrationService;
    private RegistrationRequest registrationRequest;
    private Role role;
    private User user;

    @BeforeEach
    void setUp() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.registrationService =
                new RegistrationService(mapper, jwtHandler, userService, roleService,
                        profileService, passwordEncoder);
        this.registrationRequest = new RegistrationRequest("johndoe@gmail.com", "Qwerty123%");
        Profile profile = new Profile(1L, "", "", 5.0, new BigDecimal(150),
                Collections.emptyList(), Collections.emptyList());
        this.role = new Role(1L, "USER");
        this.user = User.builder()
                .email(registrationRequest.getEmail())
                .password(bCryptPasswordEncoder.encode(registrationRequest.getPassword()))
                .role(role)
                .profile(profile)
                .build();
    }

    @Test
    void shouldPerformRegistration() {
        given(userService.existsByEmail(registrationRequest.getEmail())).willReturn(false);
        given(mapper.map(registrationRequest, User.class)).willReturn(user);
        given(passwordEncoder.encode(registrationRequest.getPassword())).willReturn(user.getPassword());
        given(userService.save(user)).willReturn(user);
        given(jwtHandler.generateToken(user.getUsername())).willReturn("fdlfldf");

        registrationService.performRegistration(registrationRequest);

        verify(userService).existsByEmail(registrationRequest.getEmail());
        verify(mapper).map(registrationRequest, User.class);
        verify(passwordEncoder).encode(registrationRequest.getPassword());
        verify(userService).save(user);
        verify(jwtHandler).generateToken(user.getUsername());
    }


    @Test
    void shouldSaveUserInPerformRegistrationMethod() {
        userService.save(user);

        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userService).save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    void shouldThrowExceptionWhenRoleNotFound() {
        String message = String.format("Role with ID %d not found", role.getId());

        given(roleService.findById(1L)).willThrow(new RoleNotFoundException(message));

        assertThatThrownBy(() -> roleService.findById(1L))
                .isInstanceOf(RoleNotFoundException.class)
                .hasMessage(message);
    }


    @Test
    void shouldThrow() {
        given(userService.existsByEmail(registrationRequest.getEmail())).willReturn(true);

        assertThatThrownBy(() -> registrationService.performRegistration(registrationRequest))
                .isInstanceOf(RegistrationFailedException.class)
                .hasMessage("Provided email is already registered.");
    }
}