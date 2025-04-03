package com.easy.finance.context.role.application.usecase;

import com.easy.finance.context.role.data.RoleData;
import com.easy.finance.context.role.domain.port.RoleRepository;
import com.easy.finance.utils.constants.ErrorMessages;
import com.easy.finance.utils.exceptions.NonExistenceException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteByIdRoleUseCaseTest {

    @InjectMocks
    private DeleteByIdRoleUseCase deleteByIdRoleUseCase;

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
    void deleteByIdSuccess() throws NonExistenceException {
        when(roleRepository.findById(any(Long.class))).thenReturn(Optional.of(roleData.getRoleActive()));

        deleteByIdRoleUseCase.deleteById(roleData.getRoleActive().getId());

        verify(roleRepository).findById(any(Long.class));
        verify(roleRepository).deleteById(any(Long.class));
    }

    @Test
    void deleteByIdNonExistenceException() {
        when(roleRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NonExistenceException exception = assertThrows(
                NonExistenceException.class,
                () -> deleteByIdRoleUseCase.deleteById(roleData.getRoleActive().getId())
        );

        assertEquals(exception.getMessage(), errorMessages.NON_EXISTENT_DATA);

        verify(roleRepository).findById(any(Long.class));
        verify(roleRepository, never()).deleteById(any(Long.class));
    }

}