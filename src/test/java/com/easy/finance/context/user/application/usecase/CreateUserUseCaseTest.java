package com.easy.finance.context.user.application.usecase;

import com.easy.finance.context.user.data.UserData;
import com.easy.finance.context.user.domain.model.User;
import com.easy.finance.context.user.domain.port.UserRepository;
import com.easy.finance.utils.constants.ErrorMessages;
import com.easy.finance.utils.exceptions.DuplicatedException;
import com.easy.finance.utils.exceptions.InvalidBodyException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private static ErrorMessages errorMessages;
    private static UserData userData;

    @BeforeAll
    static void setUp() {
        errorMessages = new ErrorMessages();
        userData = new UserData();
    }

    @Test
    void createSuccess() throws InvalidBodyException, DuplicatedException {
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn(userData.getEncodedPassword());
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
        when(userRepository.create(any(User.class))).thenReturn(userData.getUserCreateValid());

        User response = createUserUseCase.create(userData.getUserCreateValid());

        assertNotNull(response);
        assertEquals(response, userData.getUserCreateValid());

        verify(passwordEncoder).encode(any(CharSequence.class));
        verify(userRepository).findByEmail(any(String.class));
        verify(userRepository).create(any(User.class));
    }

    @Test
    void createFailedInvalidBodyException() {
        InvalidBodyException exception = assertThrows(
                InvalidBodyException.class,
                () -> createUserUseCase.create(userData.getUserCreateInvalid())
        );

        assertEquals(exception.getMessage(), errorMessages.INVALID_BODY);

        verify(passwordEncoder).encode(any(CharSequence.class));
        verify(userRepository, never()).findByEmail(any(String.class));
        verify(userRepository, never()).create(any(User.class));
    }

    @Test
    void createFailedInvalidBodyExceptionByPassword() {
        InvalidBodyException exception = assertThrows(
                InvalidBodyException.class,
                () -> createUserUseCase.create(userData.getUserCreateNoPassword())
        );

        assertEquals(exception.getMessage(), errorMessages.INVALID_BODY);

        verify(passwordEncoder, never()).encode(any(CharSequence.class));
        verify(userRepository, never()).findByEmail(any(String.class));
        verify(userRepository, never()).create(any(User.class));
    }

    @Test
    void createFailedDuplicatedException() {
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn(userData.getEncodedPassword());
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(userData.getUserActive()));

        DuplicatedException exception = assertThrows(
                DuplicatedException.class,
                () -> createUserUseCase.create(userData.getUserActive())
        );

        assertEquals(exception.getMessage(), errorMessages.DUPLICATED);

        verify(passwordEncoder).encode(any(CharSequence.class));
        verify(userRepository).findByEmail(any(String.class));
        verify(userRepository, never()).create(any(User.class));
    }

}