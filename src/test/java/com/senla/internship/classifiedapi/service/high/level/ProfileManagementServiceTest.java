package com.senla.internship.classifiedapi.service.high.level;

import com.senla.internship.classifiedapi.dto.request.ProfileRequest;
import com.senla.internship.classifiedapi.model.account.Profile;
import com.senla.internship.classifiedapi.model.account.Role;
import com.senla.internship.classifiedapi.model.account.User;
import com.senla.internship.classifiedapi.security.authentication.AuthenticationFacade;
import com.senla.internship.classifiedapi.service.low.level.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileManagementServiceTest {
    private User user;
    private ProfileRequest profileRequest;
    @Mock private UserService userService;
    @Mock private AuthenticationFacade authenticationFacade;
    @Mock private Authentication authentication;
    private ProfileManagementService profileManagementService;

    @BeforeEach
    void setUp() {
        this.profileManagementService = new ProfileManagementService(userService, authenticationFacade);
        this.profileRequest = new ProfileRequest("John", "+79057240914");
        Role role = new Role(1L, "USER");
        Profile profile = new Profile(1L, "John", "+79057240914", 5.0,
                new BigDecimal("150"), Collections.emptyList(), Collections.emptyList());
        this.user = new User(1L, "johndoe@gmail.com", "Qwerty123%", role, profile);
    }

    @Test
    void editProfile() {
        given(authentication.getName()).willReturn("johndoe@gmail.com");
        given(authenticationFacade.getAuthentication()).willReturn(authentication);
        given(userService.findByEmail(user.getEmail())).willReturn(user);
        given(userService.save(user)).willReturn(user);

        profileManagementService.performProfileEditing(profileRequest);

        verify(authentication).getName();
        verify(authenticationFacade).getAuthentication();
        verify(userService).findByEmail(user.getEmail());
        verify(userService).save(user);
    }
}