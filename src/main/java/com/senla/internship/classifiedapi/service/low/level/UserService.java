package com.senla.internship.classifiedapi.service.low.level;

import com.senla.internship.classifiedapi.exception.BadRequestException;
import com.senla.internship.classifiedapi.exception.DataNotFoundException;
import com.senla.internship.classifiedapi.exception.UserNotFoundException;
import com.senla.internship.classifiedapi.model.account.User;
import com.senla.internship.classifiedapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Custom implementation of the {@link IUserService} interface.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Slf4j
@Service
public class UserService implements IUserService {
    private static final String DATA_NOT_FOUND_MESSAGE = "Data not found";
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, readOnly = true)
    public User findByEmail(String email) throws UserNotFoundException {
        String message = String.format("User with email %s is not found!", email);
        log.info("User has been found by email");
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(message));
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, readOnly = true)
    public User findById(Long id) throws UserNotFoundException {
        String message = String.format("User with ID %d is not found!", id);
        log.info("User has been found by id");
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(message));
    }

    @Override
    public boolean existsByEmail(String email) {
        log.info("User checked if exists by email");
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsById(Long id) {
        log.info("User checked if exists by id");
        return userRepository.existsById(id);
    }

    @Override
    public User save(User user) throws BadRequestException {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() throws DataNotFoundException {
        log.debug("Delegating users searching to the {}", userRepository.getClass().getName());
        List<User> users = userRepository.findAll();

        log.debug("Checking if a list of users is empty");
        if (users.isEmpty()) {
            throw new DataNotFoundException(DATA_NOT_FOUND_MESSAGE);
        }

        log.info("List of users has been returned");
        return users;
    }
}
