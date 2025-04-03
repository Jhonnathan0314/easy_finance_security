package com.easy.finance.context.role.application.usecase;

import com.easy.finance.context.role.data.RoleData;
import com.easy.finance.context.role.domain.model.Role;
import com.easy.finance.context.role.domain.port.RoleRepository;
import com.easy.finance.utils.constants.ErrorMessages;
import com.easy.finance.utils.exceptions.DuplicatedException;
import com.easy.finance.utils.exceptions.InvalidBodyException;
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
class CreateRoleUseCaseTest {

    @InjectMocks
    private CreateRoleUseCase createRoleUseCase;

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
    void createRoleSuccess() throws DuplicatedException, InvalidBodyException {
        when(roleRepository.findByName(any(String.class))).thenReturn(Optional.empty());
        when(roleRepository.create(any(Role.class))).thenReturn(roleData.getRoleCreateValid());

        Role response = createRoleUseCase.create(roleData.getRoleCreateValid());

        assertNotNull(response);
        assertEquals(response, roleData.getRoleCreateValid());

        verify(roleRepository).findByName(any(String.class));
        verify(roleRepository).create(any(Role.class));
    }

    @Test
    void createRoleSuccessDuplicatedException() {
        when(roleRepository.findByName(any(String.class))).thenReturn(Optional.of(roleData.getRoleActive()));

        DuplicatedException exception = assertThrows(
                DuplicatedException.class,
                () -> createRoleUseCase.create(roleData.getRoleActive())
        );

        assertEquals(exception.getMessage(), errorMessages.DUPLICATED);

        verify(roleRepository).findByName(any(String.class));
        verify(roleRepository, never()).create(any(Role.class));
    }

    @Test
    void createRoleSuccessInvalidBodyException() {
        InvalidBodyException exception = assertThrows(
                InvalidBodyException.class,
                () -> createRoleUseCase.create(roleData.getRoleCreateInvalid())
        );

        assertEquals(exception.getMessage(), errorMessages.INVALID_BODY);

        verify(roleRepository, never()).findByName(any(String.class));
        verify(roleRepository, never()).create(any(Role.class));
    }

}