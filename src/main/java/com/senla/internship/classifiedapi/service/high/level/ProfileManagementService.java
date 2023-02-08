package com.senla.internship.classifiedapi.service.high.level;

import com.senla.internship.classifiedapi.dto.request.ProfileRequest;
import com.senla.internship.classifiedapi.model.account.Profile;
import com.senla.internship.classifiedapi.model.account.User;
import com.senla.internship.classifiedapi.security.authentication.IAuthenticationFacade;
import com.senla.internship.classifiedapi.service.low.level.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Solves problems of business logic related to profile-specific-requirements
 * @author Vlas Nagibin
 * @version 1.0
 */
@Slf4j
@Service
public class ProfileManagementService {
    private final IUserService userService;
    private final IAuthenticationFacade authenticationFacade;

    /**
     * Constructs a {@code ProfileManagementService} with a
     * user service and authentication facade.
     * @param userService the user service
     * @param authenticationFacade the authentication facade
     */
    public ProfileManagementService(IUserService userService,
                                    IAuthenticationFacade authenticationFacade) {
        this.userService = userService;
        this.authenticationFacade = authenticationFacade;
    }

    /**
     * Performs profile editing.
     * @param profileRequest the profile data access object
     * @return the edited profile
     */
    public Profile performProfileEditing(ProfileRequest profileRequest) {
        log.debug("Getting user's username");
        String email = authenticationFacade.getAuthentication().getName();
        User user = userService.findByEmail(email);

        editProfile(user, profileRequest);

        user = userService.save(user);
        log.info("Edited profile has been returned to the Controller Layer");
        return user.getProfile();
    }

    private void editProfile(User user, ProfileRequest profileRequest) {
        user.getProfile().setName(profileRequest.getName());
        user.getProfile().setPhone(profileRequest.getPhone());
    }
}
