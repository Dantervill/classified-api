package com.senla.internship.classifiedapi.repository;

import com.senla.internship.classifiedapi.model.account.Profile;
import com.senla.internship.classifiedapi.model.advertisement.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * The {@code AdvertisementRepository} class is the specific extension of {@link JpaRepository}.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    /**
     * Finds an advertisement by id and user profile.
     * @param id the id. Must not be null.
     * @param profile the user profile. Must not be null.
     * @return the optional advertisement
     */
    @Query("select a from Advertisement a where a.id = ?1 and a.profile = ?2")
    Optional<Advertisement> findByIdAndProfile(Long id, Profile profile);

    /**
     * Deletes an advertisement by id, user profile where active advertisement is true.
     * @param id the id. Must not be null.
     * @param profile the user profile. Must not be null.
     */
    @Transactional
    @Modifying
    @Query("delete from Advertisement a where a.id = ?1 and a.profile = ?2 and a.active = true")
    void deleteAdvertisementByIdAndProfileAndActiveIsTrue(Long id, Profile profile);


    /**
     * Finds all advertisements by user profile where active advertisement is false.
     * @param profile the user profile. Must not be null.
     * @return the list of advertisements
     */
    List<Advertisement> findAllByProfileAndActiveIsFalse(Profile profile);

    /**
     * Finds all advertisements by user profile where active advertisement is true.
     * @param profile the user profile. Must not be null.
     * @return the list of advertisements
     */
    List<Advertisement> findAllByProfileAndActiveIsTrue(Profile profile);

    /**
     * Finds all advertisements by title, minimum price and maximum price, where active
     * advertisement is true, and sorted by vip and user profile rating in descending order.
     * @param title the title. Must not be null.
     * @param min the minimum price. Must not be null.
     * @param max the maximum price. Must not be null.
     * @return the list of advertisements
     */
    @Query("select new Advertisement (a.vip, a.active, a.type, a.title, a.condition, a.brand, a.description, a.price, a.location, a.profile) " +
            "from Advertisement a " +
            "where (upper(a.title) like upper(concat('%', ?1, '%')) or upper(a.description) like upper(concat('%', ?1, '%'))) " +
            "and a.price >= ?2 and a.price <= ?3 and a.active = true " +
            "order by a.vip desc, a.profile.rating desc ")
    List<Advertisement> findAllByTitleAndFilterByPrice(String title, BigDecimal min, BigDecimal max);

    /**
     * Finds all advertisements by title where active advertisement is true, and sorted by
     * vip and user profile rating in descending order.
     * @param title the title. Must not be null.
     * @return the list of advertisements
     */
    @Query("select new Advertisement (a.vip, a.active, a.type, a.title, a.condition, a.brand, a.description, a.price, a.location, a.profile) " +
            "from Advertisement a " +
            "where (upper(a.title) like upper(concat('%', ?1, '%')) or upper(a.description) like upper(concat('%', ?1, '%'))) " +
            "and a.active = true " +
            "order by a.vip desc, a.profile.rating desc ")
    List<Advertisement> findAllByTitle(String title);
}
