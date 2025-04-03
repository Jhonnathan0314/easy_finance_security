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
class FindByNameRoleUseCaseTest {

    @InjectMocks
    private FindByNameRoleUseCase findByNameRoleUseCase;

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
    void findByNameRoleSuccess() throws NoResultsException {
        when(roleRepository.findByName(any(String.class))).thenReturn(Optional.of(roleData.getRoleActive()));

        Role response = findByNameRoleUseCase.findByName(roleData.getRoleActive().getName());

        assertNotNull(response);
        assertEquals(response, roleData.getRoleActive());

        verify(roleRepository).findByName(any(String.class));
    }

    @Test
    void findByNameRoleNoResultsException() {
        when(roleRepository.findByName(any(String.class))).thenReturn(Optional.empty());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () -> findByNameRoleUseCase.findByName(roleData.getRoleActive().getName())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_RESULTS);

        verify(roleRepository).findByName(any(String.class));

    }

}