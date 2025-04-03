package com.easy.finance.context.role.application.usecase;

import com.easy.finance.context.role.data.RoleData;
import com.easy.finance.context.role.domain.model.Role;
import com.easy.finance.context.role.domain.port.RoleRepository;
import com.easy.finance.utils.constants.ErrorMessages;
import com.easy.finance.utils.exceptions.NoResultsException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindByIdRoleUseCaseTest {

    @InjectMocks
    private FindByIdRoleUseCase findByIdRoleUseCase;

    @Mock
    private RoleRepository roleRepository;

    private static ErrorMessages errorMessages;
    private static RoleData roleData;

    @BeforeAll
    static void setUp() {
        errorMessages = new ErrorMessages();
        roleData = new RoleData();
    }

    @Test
    void findByIdRoleSuccess() throws NoResultsException {
        when(roleRepository.findById(any(Long.class))).thenReturn(Optional.of(roleData.getRoleActive()));

        Role response = findByIdRoleUseCase.findById(roleData.getRoleActive().getId());

        assertNotNull(response);
        assertEquals(response, roleData.getRoleActive());

        verify(roleRepository).findById(any(Long.class));
    }

    @Test
    void findByIdRoleNoResultsException() {
        when(roleRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () -> findByIdRoleUseCase.findById(roleData.getRoleActive().getId())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_RESULTS);

        verify(roleRepository).findById(any(Long.class));
    }

}