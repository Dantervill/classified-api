package com.senla.internship.classifiedapi.service.low.level;

import com.senla.internship.classifiedapi.model.account.Profile;
import com.senla.internship.classifiedapi.model.advertisement.Advertisement;

import java.math.BigDecimal;
import java.util.List;

/**
 * Core interface which processes advertisement-specific-data.
 * @author Vlas Nagibin
 * @version 1.0
 */
public interface IAdvertisementService {

    /**
     * Finds an advertisement by id and user profile.
     * @param id the id
     * @param profile the user profile
     * @return the advertisement
     */
    Advertisement findByIdAndProfile(Long id, Profile profile);

    /**
     * Finds an advertisement by id.
     * @param id the id
     * @return the advertisement
     */
    Advertisement findById(Long id);

    /**
     * Saves an advertisement.
     * @param advertisement the advertisement
     * @return the saved advertisement
     */
    Advertisement save(Advertisement advertisement);

    /**
     * Deletes an advertisement by id, user profile where active boolean is true.
     * @param id the id
     * @param profile the user profile
     */
    void deleteAdvertisementByIdAndProfileAndActiveIsTrue(Long id, Profile profile);

    /**
     * Finds vertisements by title
     * @param title the title
     * @return the list of advertisements
     */
    List<Advertisement> findAllByTitle(String title);

    /**
     * Finds advertisements by id, minimum price and maximum price.
     * @param title the title
     * @param min the minimum price
     * @param max the maximum price
     * @return the list of advertisements
     */
    List<Advertisement> findAllByTitleAndMinPriceAndMaxPrice(String title, BigDecimal min, BigDecimal max);

    /**
     * Finds advertisements by user profile where active boolean is false.
     * @param profile the user profile
     * @return the list of advertisements
     */
    List<Advertisement> findAllByProfileAndActiveIsFalse(Profile profile);

    /**
     * Finds advertisements by user profile where active boolean is true.
     * @param profile the user profile
     * @return the list of advertisements
     */
    List<Advertisement> findAllByProfileAndActiveIsTrue(Profile profile);

    /**
     * Deletes an advertisement bu id.
     * @param id the id
     */
    void deleteById(Long id);
}
