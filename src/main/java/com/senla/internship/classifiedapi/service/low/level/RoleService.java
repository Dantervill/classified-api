package com.senla.internship.classifiedapi.service.low.level;

import com.senla.internship.classifiedapi.exception.RoleNotFoundException;
import com.senla.internship.classifiedapi.model.account.Role;
import com.senla.internship.classifiedapi.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Custom implementation of the {@link IRoleService} interface.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Slf4j
@Service
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, readOnly = true)
    public Role findById(Long id) throws RoleNotFoundException {
        String message = String.format("Role with ID %d not found", id);
        log.info("User has been found by id");
        return roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(message));
    }
}
