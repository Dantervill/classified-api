package com.senla.internship.classifiedapi.repository;

import com.senla.internship.classifiedapi.model.account.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The {@code RoleRepository} class is the specific extension of {@link JpaRepository}.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
