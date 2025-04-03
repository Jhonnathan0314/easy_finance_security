package com.easy.finance.context.user.application.usecase;

import com.easy.finance.context.user.data.UserData;
import com.easy.finance.context.user.domain.model.User;
import com.easy.finance.context.user.domain.port.UserRepository;
import com.easy.finance.utils.constants.ErrorMessages;
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
class FindByEmailUserUseCaseTest {

    @InjectMocks
    private FindByEmailUserUseCase findByEmailUserUseCase;

    @Mock
    private UserRepository userRepository;

    private static UserData userData;

    @BeforeAll
    static void setUp() {
        userData = new UserData();
    }

    @Test
    void findByNameRoleSuccess() {
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(userData.getUserActive()));

        User response = findByEmailUserUseCase.findByEmail(userData.getUserActive().getName()).orElse(null);

        assertNotNull(response);
        assertEquals(response, userData.getUserActive());

        verify(userRepository).findByEmail(any(String.class));
    }

}