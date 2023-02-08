package com.senla.internship.classifiedapi.service.high.level;

import com.senla.internship.classifiedapi.dto.request.RegistrationRequest;
import com.senla.internship.classifiedapi.dto.response.TokenResponse;
import com.senla.internship.classifiedapi.exception.RegistrationFailedException;
import com.senla.internship.classifiedapi.model.account.*;
import com.senla.internship.classifiedapi.model.advertisement.Advertisement;
import com.senla.internship.classifiedapi.security.jwt.JwtHandler;
import com.senla.internship.classifiedapi.service.low.level.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Solves problems of business logic related to registration-specific-requirements
 * @author Vlas Nagibin
 * @version 1.0
 */
@Slf4j
@Service
public class RegistrationService {
    private static final String REG_ERROR_MESSAGE = "Provided email is already registered.";
    private final ModelMapper mapper;
    private final JwtHandler jwtHandler;
    private final IUserService userService;
    private final IRoleService roleService;
    private final IProfileService profileService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a {@code RegistrationService} with a model mapper, jwt handler,
     * user service, role service, profile service, and password encoder.
     * @param mapper the model mapper
     * @param jwtHandler the jwt handler
     * @param userService the user service
     * @param roleService the role service
     * @param profileService the profile service
     * @param passwordEncoder the password encoder
     */
    @Autowired
    public RegistrationService(ModelMapper mapper,
                               JwtHandler jwtHandler,
                               UserService userService,
                               RoleService roleService,
                               ProfileService profileService,
                               PasswordEncoder passwordEncoder) {
        this.mapper = mapper;
        this.jwtHandler = jwtHandler;
        this.userService = userService;
        this.roleService = roleService;
        this.profileService = profileService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Performs registration.
     * @param registrationRequest the registration data access object
     * @return the jwt token
     */
    public TokenResponse performRegistration(RegistrationRequest registrationRequest) {
        if (userService.existsByEmail(registrationRequest.getEmail())) {
            log.error("{} thrown", RegistrationFailedException.class.getName());
            throw new RegistrationFailedException(REG_ERROR_MESSAGE);
        }

        log.debug("Registering a new user");
        User user = buildUser(registrationRequest);
        user = userService.save(user);

        log.debug("Generating a jwt token");
        String generatedToken = jwtHandler.generateToken(user.getUsername());

        TokenResponse tokenResponse = new TokenResponse(generatedToken, LocalDateTime.now(),
                LocalDateTime.from(LocalDateTime.now().plusMinutes(60)));
        log.info("A token has been returned to Controller Layer");
        return tokenResponse;
    }

    private User buildUser(RegistrationRequest registrationRequest) {
        log.debug("Creating a new user");
        User user = mapper.map(registrationRequest, User.class);
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setProfile(buildDefaultProfile());
        user.setRole(getUserRole());
        return user;
    }

    private Profile buildDefaultProfile() {
        log.debug("Creating a new profile");
        String defaultName = "";
        String defaultPhone = "";
        double defaultRating = 5.0;
        BigDecimal defaultBalance = new BigDecimal(150);
        List<Advertisement> defaultAds = Collections.emptyList();
        List<Message> defaultMessages = Collections.emptyList();

        Profile profile = Profile.builder()
                .name(defaultName)
                .phone(defaultPhone)
                .rating(defaultRating)
                .balance(defaultBalance)
                .advertisements(defaultAds)
                .messages(defaultMessages)
                .build();
        return profileService.save(profile);
    }

    private Role getUserRole() {
        log.debug("Getting a user role");
        long defaultRoleId = 1;
        return roleService.findById(defaultRoleId);
    }
}
