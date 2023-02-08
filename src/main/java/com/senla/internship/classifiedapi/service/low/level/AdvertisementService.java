package com.senla.internship.classifiedapi.service.low.level;

import com.senla.internship.classifiedapi.exception.AdvertisementNotFoundException;
import com.senla.internship.classifiedapi.exception.DataNotFoundException;
import com.senla.internship.classifiedapi.model.account.Profile;
import com.senla.internship.classifiedapi.model.advertisement.Advertisement;
import com.senla.internship.classifiedapi.repository.AdvertisementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Custom implementation of the {@link IAdvertisementService} interface.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Slf4j
@Service
public class AdvertisementService implements IAdvertisementService {
    private static final String DATA_NOT_FOUND_MESSAGE = "Data not found";
    private final AdvertisementRepository advertisementRepository;

    @Autowired
    public AdvertisementService(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, readOnly = true)
    public Advertisement findByIdAndProfile(Long id, Profile profile) throws AdvertisementNotFoundException {
        String message = String.format("Advertisement with id %d not found", id);
        return advertisementRepository.findByIdAndProfile(id, profile)
                .orElseThrow(() -> new AdvertisementNotFoundException(message));
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, readOnly = true)
    public Advertisement findById(Long id) throws AdvertisementNotFoundException {
        String message = String.format("Advertisement with id %d not found", id);
        return advertisementRepository.findById(id)
                .orElseThrow(() -> new AdvertisementNotFoundException(message));
    }

    @Override
    public Advertisement save(Advertisement advertisement) {
        log.info("Advertisement has been saved and returned to the Service Layer");
        return advertisementRepository.save(advertisement);
    }

    @Override
    public void deleteAdvertisementByIdAndProfileAndActiveIsTrue(Long id, Profile profile) {
        advertisementRepository.deleteAdvertisementByIdAndProfileAndActiveIsTrue(id, profile);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, readOnly = true)
    public List<Advertisement> findAllByTitle(String title) throws DataNotFoundException {
        log.debug("Collecting advertisements called by name {} in title and description", title);
        List<Advertisement> advertisements = advertisementRepository
                .findAllByTitle(title);

        if (advertisements.isEmpty()) {
            log.error("{} occurred due to lack of data", DataNotFoundException.class.getName());
            throw new DataNotFoundException(DATA_NOT_FOUND_MESSAGE);
        }

        log.info("Advertisements have been returned to the Service Layer");
        return advertisements;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, readOnly = true)
    public List<Advertisement> findAllByTitleAndMinPriceAndMaxPrice(String title, BigDecimal min, BigDecimal max) throws DataNotFoundException {
        log.debug("Collecting advertisements called by name {} in title and description and filtered by min price {} and max price {}",
                title, min, max);
        List<Advertisement> advertisements = advertisementRepository
                .findAllByTitleAndFilterByPrice(title, min, max);

        if (advertisements.isEmpty()) {
            log.error("{} occurred due to the lack of data", DataNotFoundException.class.getName());
            throw new DataNotFoundException(DATA_NOT_FOUND_MESSAGE);
        }

        log.info("Advertisements have been returned to the Service Layer");
        return advertisements;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, readOnly = true)
    public List<Advertisement> findAllByProfileAndActiveIsFalse(Profile profile) throws DataNotFoundException {
        List<Advertisement> soldAds = advertisementRepository.findAllByProfileAndActiveIsFalse(profile);

        if (soldAds.isEmpty()) {
            throw new DataNotFoundException(DATA_NOT_FOUND_MESSAGE);
        }

        return soldAds;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, readOnly = true)
    public List<Advertisement> findAllByProfileAndActiveIsTrue(Profile profile) throws DataNotFoundException {
        List<Advertisement> activeAds = advertisementRepository.findAllByProfileAndActiveIsTrue(profile);

        if (activeAds.isEmpty()) {
            throw new DataNotFoundException(DATA_NOT_FOUND_MESSAGE);
        }

        return activeAds;
    }

    @Override
    public void deleteById(Long id) {
        advertisementRepository.deleteById(id);
    }
}
