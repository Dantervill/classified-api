package com.senla.internship.classifiedapi.service.high.level;

import com.senla.internship.classifiedapi.dto.request.AdvertisementRequest;
import com.senla.internship.classifiedapi.exception.NotEnoughMoneyException;
import com.senla.internship.classifiedapi.model.account.Profile;
import com.senla.internship.classifiedapi.model.advertisement.*;
import com.senla.internship.classifiedapi.security.authentication.IAuthenticationFacade;
import com.senla.internship.classifiedapi.service.low.level.IAdvertisementService;
import com.senla.internship.classifiedapi.service.low.level.ILocationService;
import com.senla.internship.classifiedapi.service.low.level.IProfileService;
import com.senla.internship.classifiedapi.service.low.level.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Solves problems of business logic related to advertisement-specific-requirements
 * @author Vlas Nagibin
 * @version 1.0
 */
@Slf4j
@Service
@Transactional
public class AdvertisementManagementService {
    private final ModelMapper mapper;
    private final IUserService userService;
    private final IProfileService profileService;
    private final ILocationService locationService;
    private final IAdvertisementService advertisementService;
    private final IAuthenticationFacade authenticationFacade;

    /**
     * Constructs an {@code AdvertisementManagementService} with a model mapper,
     * user service, profile service, location service, advertisement service,
     * authentication facade.
     * @param mapper the model mapper
     * @param userService the user service
     * @param profileService the profile service
     * @param locationService the location service
     * @param advertisementService the advertisement service
     * @param authenticationFacade the authentication facade
     */
    @Autowired
    public AdvertisementManagementService(ModelMapper mapper,
                                          IUserService userService,
                                          IProfileService profileService,
                                          ILocationService locationService,
                                          IAdvertisementService advertisementService,
                                          IAuthenticationFacade authenticationFacade) {
        this.mapper = mapper;
        this.userService = userService;
        this.profileService = profileService;
        this.locationService = locationService;
        this.advertisementService = advertisementService;
        this.authenticationFacade = authenticationFacade;
    }


    /**
     * Adds a new advertisement.
     * @param advertisementRequest the advertisement data access object
     * @return the new advertisement
     */
    public Advertisement addAdvertisement(AdvertisementRequest advertisementRequest) {
        log.debug("Adding a new advertisement");
        Profile current = userService.findByEmail(authenticationFacade.getAuthentication().getName())
                .getProfile();
        Location location = mapper.map(advertisementRequest.getLocation(), Location.class);
        Location savedLocation = locationService.save(location);
        Advertisement advertisement = buildAdvertisement(advertisementRequest, current, savedLocation);
        advertisement = advertisementService.save(advertisement);
        log.info("A new advertisement has been returned to the Controller Layer");
        return advertisement;
    }

    /**
     * Deletes an advertisement.
     * @param advertisementId the advertisement id
     * @return the list of advertisements
     */
    public List<Advertisement> deleteAdvertisement(Long advertisementId) {
        log.debug("Deleting advertisement");
        Profile current = userService.findByEmail(authenticationFacade.getAuthentication().getName()).getProfile();
        Advertisement advertisement = advertisementService.findByIdAndProfile(advertisementId, current);
        Location location = advertisement.getLocation();
        advertisementService.deleteById(advertisement.getId());
        locationService.deleteById(location.getId());
        List<Advertisement> advertisements = advertisementService.findAllByProfileAndActiveIsTrue(current);
        log.info("A List of advertisements has been returned to the Controller Layer");
        return advertisements;
    }

    /**
     * Edits an advertisement.
     * @param advertisementId the advertisement id
     * @param advertisementRequest the advertisement data access object
     * @return the edited advertisement
     */
    public Advertisement editAdvertisement(Long advertisementId, AdvertisementRequest advertisementRequest) {
        log.debug("Editing advertisement");
        Profile current = userService.findByEmail(authenticationFacade.getAuthentication().getName()).getProfile();
        Advertisement advertisement = advertisementService.findByIdAndProfile(advertisementId, current);

        Advertisement editedAdvertisement = buildEditedAdvertisement(advertisementRequest, advertisement);
        Location location = mapper.map(advertisementRequest.getLocation(), Location.class);
        location.setId(advertisement.getLocation().getId());
        editedAdvertisement.setLocation(location);

        editedAdvertisement = advertisementService.save(editedAdvertisement);
        log.info("Edited advertisement has been returned to the Controller Layer");
        return editedAdvertisement;
    }

    private Advertisement buildEditedAdvertisement(AdvertisementRequest advertisementRequest, Advertisement advertisement) {
        Advertisement editedAdvertisement = mapper.map(advertisementRequest, Advertisement.class);
        editedAdvertisement.setId(advertisement.getId());
        editedAdvertisement.setActive(advertisement.isActive());
        editedAdvertisement.setVip(advertisement.isVip());
        editedAdvertisement.setComments(advertisement.getComments());
        editedAdvertisement.setProfile(advertisement.getProfile());
        return editedAdvertisement;
    }

    /**
     * Makes an advertisement vip.
     * @param advertisementId the advertisement id
     * @return the vip advertisement
     * @throws NotEnoughMoneyException the exception
     */
    public Advertisement makeAdVIP(Long advertisementId) throws NotEnoughMoneyException {
        boolean vip = true;
        log.debug("Setting an advertisement to VIP");
        Profile current = userService.findByEmail(authenticationFacade.getAuthentication().getName()).getProfile();
        Advertisement advertisement = advertisementService.findByIdAndProfile(advertisementId, current);
        makePayment(current);
        profileService.save(current);
        advertisement.setVip(vip);
        advertisement = advertisementService.save(advertisement);
        log.debug("A VIP advertisement has been returned to the Controller Layer");
        return advertisement;
    }

    /**
     * Gets sold advertisements of an authenticated user.
     * @return the list of sold advertisements
     */
    public List<Advertisement> getMySoldAdvertisements() {
        log.debug("Getting user's sold advertisements");
        Profile current = userService.findByEmail(authenticationFacade.getAuthentication().getName()).getProfile();
        List<Advertisement> soldAdvertisements = advertisementService.findAllByProfileAndActiveIsFalse(current);
        log.info("A list of sold advertisements of a current user has been returned to the Controller Layer");
        return soldAdvertisements;
    }

    /**
     * Gets sold advertisements of a user
     * @param userId the user id
     * @return the list of advertisements of a user
     */
    public List<Advertisement> getUserSoldAdvertisements(Long userId) {
        log.debug("Getting unknown user's sold advertisements");
        Profile profile = userService.findById(userId).getProfile();
        List<Advertisement> soldAdvertisements = advertisementService.findAllByProfileAndActiveIsFalse(profile);
        log.info("A list of sold advertisements of an unknown user has been returned to the Controller Layer");
        return soldAdvertisements;
    }

    private Advertisement buildAdvertisement(AdvertisementRequest dto, Profile current, Location location) {
        boolean defaultVIP = false;
        boolean defaultActive = true;
        List<Comment> defaultComments = Collections.emptyList();
        return Advertisement.builder()
                .active(defaultActive)
                .vip(defaultVIP)
                .profile(current)
                .type(Type.valueOf(dto.getType()))
                .title(dto.getTitle())
                .condition(Condition.valueOf(dto.getCondition()))
                .brand(Brand.valueOf(dto.getBrand()))
                .description(dto.getDescription())
                .price(dto.getPrice())
                .location(location)
                .comments(defaultComments)
                .build();
    }

    private void makePayment(Profile current) throws NotEnoughMoneyException {
        BigDecimal vipPrice = new BigDecimal(150);

        if (current.getBalance().longValue() < vipPrice.longValue()) {
            throw new NotEnoughMoneyException("Not enough money.");
        }
        current.setBalance(current.getBalance().subtract(vipPrice));
    }
}
