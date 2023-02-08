package com.senla.internship.classifiedapi.service.high.level;

import com.senla.internship.classifiedapi.dto.request.AdvertisementRequest;
import com.senla.internship.classifiedapi.dto.request.LocationRequest;
import com.senla.internship.classifiedapi.model.advertisement.*;
import com.senla.internship.classifiedapi.model.account.Profile;
import com.senla.internship.classifiedapi.model.account.Role;
import com.senla.internship.classifiedapi.model.account.User;
import com.senla.internship.classifiedapi.security.authentication.AuthenticationFacade;
import com.senla.internship.classifiedapi.service.low.level.AdvertisementService;
import com.senla.internship.classifiedapi.service.low.level.LocationService;
import com.senla.internship.classifiedapi.service.low.level.ProfileService;
import com.senla.internship.classifiedapi.service.low.level.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AdvertisementManagementServiceTest {
    @Mock private UserService userService;
    @Mock private ProfileService profileService;
    @Mock private LocationService locationService;
    @Mock private Authentication authentication;
    @Mock private AdvertisementService advertisementService;
    @Mock private AuthenticationFacade authenticationFacade;
    @Mock private ModelMapper mapper;
    private AdvertisementManagementService advertisementManagementService;
    private Advertisement soldAdvertisement;
    private Advertisement advertisement;
    private Location location;
    private LocationRequest locationRequest;
    private Profile profile;
    private AdvertisementRequest advertisementRequest;
    private User user;

    @BeforeEach
    void setUp() {
        this.advertisementManagementService =
                new AdvertisementManagementService(mapper, userService, profileService,
                        locationService, advertisementService, authenticationFacade);

        this.location = new Location(1L, "Moscow", "Moscovskaya Oblast'");
        this.locationRequest = new LocationRequest("Moscow", "Moscovskaya Oblast'");
        this.profile = new Profile(1L, "", "", 5.0, new BigDecimal(150),
                Collections.emptyList(), Collections.emptyList());
        this.advertisement = Advertisement.builder()
                .id(1L)
                .type(Type.ELECTRONICS)
                .title("Smth")
                .condition(Condition.SECOND_HAND)
                .description("Smth")
                .price(new BigDecimal(150))
                .active(true)
                .vip(false)
                .location(location)
                .profile(profile)
                .comments(Collections.emptyList())
                .build();

        this.soldAdvertisement = Advertisement.builder()
                .id(1L)
                .type(Type.ELECTRONICS)
                .title("Smth")
                .condition(Condition.SECOND_HAND)
                .description("Smth")
                .price(new BigDecimal(150))
                .active(false)
                .vip(false)
                .location(location)
                .profile(profile)
                .comments(Collections.emptyList())
                .build();

        this.advertisementRequest = new AdvertisementRequest("ELECTRONICS", "Title",
                "SECOND_HAND", "APPLE", "Description",
                new BigDecimal(10000), locationRequest);

        this.user = new User(1L, "johndoe@gmail.com", "Qwerty123%", new Role(1L, "USER"), profile);
    }

    @Test
    void shouldDeleteAdvertisement() {
        advertisementService.deleteById(1L);

        verify(advertisementService).deleteById(1L);
    }

    @Test
    void shouldAddAdvertisement() {
        String email = user.getEmail();

        given(authentication.getName()).willReturn(email);
        given(authenticationFacade.getAuthentication()).willReturn(authentication);
        given(userService.findByEmail(email)).willReturn(user);
        given(mapper.map(advertisementRequest.getLocation(), Location.class)).willReturn(new Location());

        advertisementManagementService.addAdvertisement(advertisementRequest);

        verify(authentication).getName();
        verify(authenticationFacade).getAuthentication();
        verify(userService).findByEmail(email);
        verify(mapper).map(advertisementRequest.getLocation(), Location.class);
    }

    @Test
    void deleteAdvertisement() {
        String email = user.getEmail();

        given(authentication.getName()).willReturn(email);
        given(authenticationFacade.getAuthentication()).willReturn(authentication);
        given(userService.findByEmail(email)).willReturn(user);
        given(advertisementService.findByIdAndProfile(advertisement.getId(), profile)).willReturn(advertisement);

        advertisementManagementService.deleteAdvertisement(advertisement.getId());

        verify(authentication).getName();
        verify(authenticationFacade).getAuthentication();
        verify(userService).findByEmail(email);
        verify(advertisementService).findByIdAndProfile(advertisement.getId(), profile);
        verify(advertisementService).findAllByProfileAndActiveIsTrue(profile);
    }

    @Test
    void editAdvertisement() {
        String email = user.getEmail();

        given(authentication.getName()).willReturn(email);
        given(authenticationFacade.getAuthentication()).willReturn(authentication);
        given(userService.findByEmail(email)).willReturn(user);
        given(advertisementService.findByIdAndProfile(1L, profile)).willReturn(advertisement);
        given(mapper.map(advertisementRequest, Advertisement.class)).willReturn(advertisement);
        given(mapper.map(locationRequest, Location.class)).willReturn(location);
        given(advertisementService.save(advertisement)).willReturn(advertisement);

        advertisementManagementService.editAdvertisement(1L, advertisementRequest);

        verify(authentication).getName();
        verify(authenticationFacade).getAuthentication();
        verify(userService).findByEmail(email);
        verify(advertisementService).findByIdAndProfile(1L, profile);
        verify(mapper).map(advertisementRequest, Advertisement.class);
        verify(mapper).map(locationRequest, Location.class);
        verify(advertisementService).save(advertisement);
    }

    @Test
    void makeAdVIP() {
        String email = user.getEmail();

        given(authentication.getName()).willReturn(email);
        given(authenticationFacade.getAuthentication()).willReturn(authentication);
        given(userService.findByEmail(email)).willReturn(user);
        given(advertisementService.findByIdAndProfile(1L, profile)).willReturn(advertisement);
        given(advertisementService.save(advertisement)).willReturn(advertisement);

        advertisementManagementService.makeAdVIP(1L);

        verify(authentication).getName();
        verify(authenticationFacade).getAuthentication();
        verify(userService).findByEmail(email);
        verify(advertisementService).findByIdAndProfile(1L, profile);
        verify(advertisementService).save(advertisement);
    }

    @Test
    void getMySoldAdvertisements() {
        String email = user.getEmail();

        given(authentication.getName()).willReturn(email);
        given(authenticationFacade.getAuthentication()).willReturn(authentication);
        given(userService.findByEmail(email)).willReturn(user);
        given(advertisementService.findAllByProfileAndActiveIsFalse(profile))
                .willReturn(Collections.singletonList(soldAdvertisement));

        advertisementManagementService.getMySoldAdvertisements();

        verify(authentication).getName();
        verify(authenticationFacade).getAuthentication();
        verify(userService).findByEmail(email);
        verify(advertisementService).findAllByProfileAndActiveIsFalse(profile);
    }

    @Test
    void getUserSoldAdvertisements() {
        Long id = user.getId();

        given(userService.findById(id)).willReturn(user);
        given(advertisementService.findAllByProfileAndActiveIsFalse(profile))
                .willReturn(Collections.singletonList(soldAdvertisement));

        advertisementManagementService.getUserSoldAdvertisements(id);

        verify(userService).findById(id);
        verify(advertisementService).findAllByProfileAndActiveIsFalse(profile);
    }
}