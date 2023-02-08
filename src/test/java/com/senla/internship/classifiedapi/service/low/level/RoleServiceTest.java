package com.senla.internship.classifiedapi.service.low.level;

import com.senla.internship.classifiedapi.exception.RoleNotFoundException;
import com.senla.internship.classifiedapi.model.account.Role;
import com.senla.internship.classifiedapi.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {
    @Mock RoleRepository roleRepository;
    private IRoleService roleService;
    private Role role;

    @BeforeEach
    void setUp() {
        this.roleService = new RoleService(roleRepository);
        this.role = new Role(1L, "USER");
    }

    @Test
    void shouldFindByIdWhenNotNull() {
        given(roleRepository.findById(role.getId()))
                .willReturn(Optional.ofNullable(role));

        roleService.findById(role.getId());

        verify(roleRepository).findById(role.getId());
    }

    @Test
    void shouldThrowExceptionWhenFindByIdIsEqualToNull() {
        String message = String.format("Role with ID %d not found", role.getId());

        assertThatThrownBy(() -> roleService.findById(role.getId()))
                .isInstanceOf(RoleNotFoundException.class)
                .hasMessage(message);
    }
}