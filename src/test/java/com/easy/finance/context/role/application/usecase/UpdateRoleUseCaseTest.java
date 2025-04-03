package com.easy.finance.context.role.application.usecase;

import com.easy.finance.context.role.data.RoleData;
import com.easy.finance.context.role.domain.model.Role;
import com.easy.finance.context.role.domain.port.RoleRepository;
import com.easy.finance.utils.constants.ErrorMessages;
import com.easy.finance.utils.exceptions.*;
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
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class UpdateRoleUseCaseTest {

    @InjectMocks
    private UpdateRoleUseCase updateRoleUseCase;

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
    void updateRoleSuccess() throws DuplicatedException, InvalidBodyException, NoChangesException, NoIdReceivedException, NonExistenceException {
        when(roleRepository.findById(any(Long.class))).thenReturn(Optional.of(roleData.getRoleActive()));
        when(roleRepository.update(any(Role.class))).thenReturn(roleData.getRoleUpdated());

        Role response = updateRoleUseCase.update(roleData.getRoleToUpdate());

        assertNotNull(response);
        assertEquals(response, roleData.getRoleUpdated());

        verify(roleRepository).findById(any(Long.class));
        verify(roleRepository).update(any(Role.class));
    }

    @Test
    void updateRoleDuplicatedException() {
        when(roleRepository.findById(any(Long.class))).thenReturn(Optional.of(roleData.getRoleActive()));
        when(roleRepository.findByName(any(String.class))).thenReturn(Optional.of(roleData.getRoleActive()));

        DuplicatedException exception = assertThrows(
                DuplicatedException.class,
                () -> updateRoleUseCase.update(roleData.getRoleToUpdate())
        );

        assertEquals(exception.getMessage(), errorMessages.DUPLICATED);

        verify(roleRepository).findByName(any(String.class));
        verify(roleRepository, never()).update(any(Role.class));
    }

    @Test
    void updateRoleInvalidBodyException() {
        InvalidBodyException exception = assertThrows(
                InvalidBodyException.class,
                () -> updateRoleUseCase.update(roleData.getRoleToUpdateInvalid())
        );

        assertEquals(exception.getMessage(), errorMessages.INVALID_BODY);

        verify(roleRepository, never()).findById(any(Long.class));
        verify(roleRepository, never()).update(any(Role.class));

    }

    @Test
    void updateRoleNoChangesException() {
        when(roleRepository.findById(any(Long.class))).thenReturn(Optional.of(roleData.getRoleActive()));

        NoChangesException exception = assertThrows(
                NoChangesException.class,
                () -> updateRoleUseCase.update(roleData.getRoleActive())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_CHANGES);

        verify(roleRepository).findById(any(Long.class));
        verify(roleRepository, never()).update(any(Role.class));
    }

    @Test
    void updateRoleNoIdReceivedException() {
        NoIdReceivedException exception = assertThrows(
                NoIdReceivedException.class,
                () -> updateRoleUseCase.update(roleData.getRoleToUpdateNoId())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_ID_RECEIVED);

        verify(roleRepository, never()).findById(any(Long.class));
        verify(roleRepository, never()).update(any(Role.class));
    }

    @Test
    void updateRoleNonExistenceException() {
        when(roleRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NonExistenceException exception = assertThrows(
                NonExistenceException.class,
                () -> updateRoleUseCase.update(roleData.getRoleToUpdate())
        );

        assertEquals(exception.getMessage(), errorMessages.NON_EXISTENT_DATA);

        verify(roleRepository).findById(any(Long.class));
        verify(roleRepository, never()).update(any(Role.class));
    }

}