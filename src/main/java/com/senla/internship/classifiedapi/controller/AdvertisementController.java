package com.senla.internship.classifiedapi.controller;

import com.senla.internship.classifiedapi.dto.request.AdvertisementRequest;
import com.senla.internship.classifiedapi.exception.BadRequestException;
import com.senla.internship.classifiedapi.model.advertisement.Advertisement;
import com.senla.internship.classifiedapi.service.high.level.*;
import com.senla.internship.classifiedapi.utils.generator.ErrorMessageGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * The {@code AdvertisementController} class is responsible for managing
 * advertisement related requests.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1")
public class AdvertisementController {
    private final AdvertisementSearchingService advertisementSearchingService;
    private final AdvertisementManagementService advertisementManagementService;

    /**
     * Create an {@code AdvertisementController} with an advertisement searching service
     * and an advertisement management service.
     * @param advertisementSearchingService the advertisement searching service
     * @param advertisementManagementService the advertisement management service
     */
    @Autowired
    public AdvertisementController(AdvertisementSearchingService advertisementSearchingService,
                                   AdvertisementManagementService advertisementManagementService) {
        this.advertisementSearchingService = advertisementSearchingService;
        this.advertisementManagementService = advertisementManagementService;
    }

    /**
     * Return the response entity of the 200 HTTP status code and a list of advertisements
     * contained a request parameter in advertisement's title or its description.
     * @param title the title of an advertisement
     * @return the response entity to a client
     */
    @GetMapping(value = "/advertisements", params = {"title"})
    public ResponseEntity<?> getAdvertisementsContainingTitle(@RequestParam(value = "title")
                                                          @NotEmpty(message = "Title should not be empty.")
                                                          @NotNull(message = "Title should not be null.")
                                                          String title) {
        log.debug("Getting a list of advertisements contained a request parameter in advertisement's title or its description");
        List<Advertisement> advertisements = advertisementSearchingService.search(title);
        log.info("A list of advertisements has been returned to user");
        return new ResponseEntity<>(advertisements, HttpStatus.OK);
    }

    /**
     * Return the response entity of the 200 HTTP status code and a list of advertisements
     * contained a request parameter in advertisement's title or its description and sorted
     * by minimum and maximum price.
     * @param title the title of an advertisement
     * @param minPrice the minimum price of an advertisement
     * @param maxPrice the maximum price of an advertisement
     * @return the response entity of a client
     */
    @GetMapping(value = "/advertisements", params = {"title", "min", "max"})
    public ResponseEntity<?> getAdsContainingTitleAndFilteredByPrice(@RequestParam(value = "title")
                                                                         @NotEmpty(message = "Title should not be empty.")
                                                                         @NotNull(message = "Title should not be null.")
                                                                         String title,

                                                                     @RequestParam("min")
                                                                     @NotNull(message = "Minimum price should not be null.")
                                                                     @Min(value = 1, message = "Minimum price should be greater than or equal to 1.")
                                                                     @Max(value = 1000000, message = "Minimum price should be less or equal to 1000000.") BigDecimal minPrice,

                                                                     @RequestParam("max")
                                                                         @NotNull(message = "Maximum price should not be null.")
                                                                         @Min(value = 1, message = "Maximum price should be greater than or equal to 1.")
                                                                         @Max(value = 1000000, message = "Maximum price should be less or equal to 1000000.")
                                                                         BigDecimal maxPrice) {

        log.debug("Getting a list of advertisements contained a request parameter in advertisement's title or its description, " +
                "and sorted by minimum and maximum price");
        List<Advertisement> advertisements = advertisementSearchingService.searchAndFilter(title, minPrice, maxPrice);
        log.info("A list of advertisements has been returned to user");
        return new ResponseEntity<>(advertisements, HttpStatus.OK);
    }

    /**
     * Return the response entity of created advertisement and the 200 HTTP status code.
     * @param advertisementRequest the advertisement data access object
     * @param bindingResult the binging result
     * @return the response entity to a client
     * @throws BadRequestException the bad request exception in case of failed validation
     */
    @PostMapping("/profile/advertisements")
    public ResponseEntity<?> createAdvertisement(@Valid @RequestBody AdvertisementRequest advertisementRequest, BindingResult bindingResult) throws BadRequestException{
        if (bindingResult.hasErrors()) {
            String message = ErrorMessageGenerator.generateErrorMessage(bindingResult);
            log.error("{} occurred because of failed validation {}", BadRequestException.class.getName(), advertisementRequest);
            throw new BadRequestException(message);
        }

        log.debug("Getting created advertisement");
        Advertisement advertisement = advertisementManagementService.addAdvertisement(advertisementRequest);
        log.info("Created advertisement has been returned to user");
        return new ResponseEntity<>(advertisement, HttpStatus.OK);
    }

    /**
     * Return the response entity of the 200 HTTP status code and a list
     * of advertisements after deleting a particular advertisement.
     * @param advertisementId the advertisement ID
     * @return the response entity to a client
     */
    @DeleteMapping("/profile/advertisements/active-advertisements/{id}")
    public ResponseEntity<?> deleteAdvertisement(@PathVariable("id") @NotNull(message = "ID should not be null.")
                                                 @Min(value = 1, message = "ID should not be less than 1.")
                                                 Long advertisementId) {

        log.debug("Getting a list of advertisements after deleting a particular advertisement");
        List<Advertisement> advertisements = advertisementManagementService.deleteAdvertisement(advertisementId);
        log.info("A list of active advertisements has been returned to a user");
        return new ResponseEntity<>(advertisements, HttpStatus.OK);
    }

    /**
     * Return the response entity of updated advertisement and the 200 HTTP status code.
     * @param advertisementId the advertisement ID
     * @param advertisementRequest the advertisement data access object
     * @param bindingResult the binding result
     * @return the response entity to a client
     * @throws BadRequestException the bad request exception in case of failed validation
     */
    @PutMapping("/profile/advertisements/active-advertisements/{id}")
    public ResponseEntity<?> updateAdvertisement(@PathVariable("id")
                                                     @NotNull(message = "Advertisement ID should not be null.")
                                                     @Min(value = 1, message = "ID should not be less than 1.")
                                                     Long advertisementId,

                                                 @Valid @RequestBody AdvertisementRequest advertisementRequest, BindingResult bindingResult) throws BadRequestException {
        if (bindingResult.hasErrors()) {
            String message = ErrorMessageGenerator.generateErrorMessage(bindingResult);
            log.error("Exception occurred with {}", advertisementRequest);
            throw new BadRequestException(message);
        }

        log.debug("Getting an edited advertisement");
        Advertisement advertisement = advertisementManagementService.editAdvertisement(advertisementId, advertisementRequest);
        log.info("Edited advertisement has been returned to user");
        return new ResponseEntity<>(advertisement, HttpStatus.OK);
    }

    /**
     * Return the response entity of a vip advertisement and the 200 HTTP status code.
     * @param advertisementId the advertisement ID which vip status is going to be changed
     * @return the response entity to a client
     */
    @PutMapping("/profile/advertisements/active-advertisements/{advertisementId}/status")
    public ResponseEntity<?> pay4Vip(@PathVariable(value = "advertisementId")
                                                    @NotNull(message = "Advertisement ID should not be null.")
                                                    @Min(value = 1, message = "Advertisement ID should be greater than or equal to 1.")
                                                    Long advertisementId) {
        log.debug("Getting a vip advertisement");
        Advertisement advertisement = advertisementManagementService.makeAdVIP(advertisementId);

        log.info("Vip advertisement has been returned to a user");
        return new ResponseEntity<>(advertisement, HttpStatus.OK);
    }

    /**
     * Return the response entity of the 200 HTTP status code and a list of sold
     * advertisements of authenticated user.
     * @return the response entity to a client
     */
    @GetMapping("/profile/advertisements/sold-advertisements")
    public ResponseEntity<?> mySoldAdvertisements() {
        log.debug("Getting a list of advertisements of authenticated user");
        List<Advertisement> advertisements = advertisementManagementService.getMySoldAdvertisements();
        log.info("Sold advertisements have been returned to user");
        return new ResponseEntity<>(advertisements, HttpStatus.OK);
    }

    /**
     * Return the response entity of the 200 HTTP status code and a list of sold
     * advertisements of a searchable user.
     * @param userId the user ID
     * @return the response entity to a client
     */
    @GetMapping("/users/{id}/profile/advertisements/sold-advertisements")
    public ResponseEntity<?> userSoldAdvertisements(@PathVariable("id")
                                                           @NotNull(message = "User ID should not be null.")
                                                           @Min(value = 1, message = "User ID should be greater than or equal to 1.")
                                                           Long userId) {

        log.debug("Getting a list of advertisements of a searchable user");
        List<Advertisement> advertisements = advertisementManagementService.getUserSoldAdvertisements(userId);
        log.info("Sold advertisements of an unfamiliar user has been returned to user");
        return new ResponseEntity<>(advertisements, HttpStatus.OK);
    }
}
