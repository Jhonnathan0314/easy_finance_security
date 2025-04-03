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

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllRoleUseCaseTest {

    @InjectMocks
    private FindAllRoleUseCase findAllRoleUseCase;

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
    void findAllRoleSuccess() throws NoResultsException {
        when(roleRepository.findAll()).thenReturn(roleData.getRolesList());

        List<Role> response = findAllRoleUseCase.findAll();

        assertNotNull(response);
        assertNotEquals(response.size(), 0);
    }

    @Test
    void findAllRoleNoResultsException() {
        when(roleRepository.findAll()).thenReturn(new LinkedList<>());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () ->  findAllRoleUseCase.findAll()
        );

        assertEquals(exception.getMessage(), errorMessages.NO_RESULTS);

        verify(roleRepository).findAll();
    }

}