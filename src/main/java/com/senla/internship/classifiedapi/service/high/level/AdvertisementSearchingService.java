package com.senla.internship.classifiedapi.service.high.level;

import com.senla.internship.classifiedapi.model.advertisement.Advertisement;
import com.senla.internship.classifiedapi.service.low.level.IAdvertisementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Solves problems of business logic related to advertisement-specific-requirements
 * @author Vlas Nagibin
 * @version 1.0
 */
@Slf4j
@Service
public class AdvertisementSearchingService {
    private final IAdvertisementService advertisementService;

    /**
     * Constructs an {@code AdvertisementSearchingService} with
     * an advertisement service.
     * @param advertisementService the advertisement service
     */
    @Autowired
    public AdvertisementSearchingService(IAdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    /**
     * Searches advertisements by title.
     * @param title the title
     * @return the list of advertisements
     */
    public List<Advertisement> search(String title) {
        log.debug("Searching advertisements by title");
        List<Advertisement> advertisements = advertisementService.findAllByTitle(title);
        log.info("Advertisements with title {} have been returned to the Controller Layer", title);
        return advertisements;
    }

    /**
     * Searches advertisements by title, minimum price, maximum price.
     * @param title the title
     * @param min the minimum price
     * @param max the maximum price
     * @return the list of advertisements
     */
    public List<Advertisement> searchAndFilter(String title, BigDecimal min, BigDecimal max) {
        log.debug("Searching advertisements by title and sorted by minimum and maximum price");
        List<Advertisement> advertisements = advertisementService.findAllByTitleAndMinPriceAndMaxPrice(title, min, max);
        log.info("Advertisements with title {}, min price {} and max price {} have been returned to the Controller Layer", title, min, max);
        return advertisements;
    }
}
