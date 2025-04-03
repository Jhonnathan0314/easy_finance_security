package com.easy.finance.context.user.application.usecase;

import com.easy.finance.context.user.data.UserData;
import com.easy.finance.context.user.domain.model.User;
import com.easy.finance.context.user.domain.port.UserRepository;
import com.easy.finance.utils.constants.ErrorMessages;
import com.easy.finance.utils.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
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
class UpdateUserUseCaseTest {

    @InjectMocks
    private UpdateUserUseCase updateUserUseCase;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private static ErrorMessages errorMessages;
    private static UserData userData;

    @BeforeEach
    void setUp() {
        errorMessages = new ErrorMessages();
        userData = new UserData();
    }

    @Test
    void updateUserSuccess() throws NonExistenceException, NoIdReceivedException, NoChangesException, InvalidBodyException, DuplicatedException {
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn(userData.getEncodedPassword());
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userData.getUserActive()));
        when(userRepository.update(any(User.class))).thenReturn(userData.getUserUpdated());

        User response = updateUserUseCase.update(userData.getUserToUpdate());

        assertNotNull(response);
        assertEquals(response, userData.getUserUpdated());

        verify(passwordEncoder).encode(any(CharSequence.class));
        verify(userRepository).findById(any(Long.class));
        verify(userRepository).update(any(User.class));
    }

    @Test
    void updateUserSuccessNoPassword() throws NonExistenceException, NoIdReceivedException, NoChangesException, InvalidBodyException, DuplicatedException {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userData.getUserActive()));
        when(userRepository.update(any(User.class))).thenReturn(userData.getUserUpdated());

        User response = updateUserUseCase.update(userData.getUserToUpdateNoPassword());

        assertNotNull(response);
        assertEquals(response, userData.getUserUpdated());

        verify(passwordEncoder, never()).encode(any(CharSequence.class));
        verify(userRepository).findById(any(Long.class));
        verify(userRepository).update(any(User.class));
    }

    @Test
    void updateUserFailedNoIdReceivedException() {
        NoIdReceivedException exception = assertThrows(
                NoIdReceivedException.class,
                () -> updateUserUseCase.update(userData.getUserToUpdateNoId())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_ID_RECEIVED);

        verify(userRepository, never()).findById(any(Long.class));
        verify(passwordEncoder, never()).encode(any(CharSequence.class));
        verify(userRepository, never()).update(any(User.class));
    }

    @Test
    void updateUserFailedInvalidBodyException() {
        InvalidBodyException exception = assertThrows(
                InvalidBodyException.class,
                () -> updateUserUseCase.update(userData.getUserToUpdateInvalid())
        );

        assertEquals(exception.getMessage(), errorMessages.INVALID_BODY);

        verify(userRepository, never()).findById(any(Long.class));
        verify(passwordEncoder, never()).encode(any(CharSequence.class));
        verify(userRepository, never()).update(any(User.class));
    }

    @Test
    void updateUserFailedNoResultsException() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NonExistenceException exception = assertThrows(
                NonExistenceException.class,
                () -> updateUserUseCase.update(userData.getUserToUpdate())
        );

        assertEquals(exception.getMessage(), errorMessages.NON_EXISTENT_DATA);

        verify(userRepository).findById(any(Long.class));
        verify(passwordEncoder, never()).encode(any(CharSequence.class));
        verify(userRepository, never()).update(any(User.class));
    }

    @Test
    void updateUserFailedNoChangesException() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userData.getUserActive()));

        NoChangesException exception = assertThrows(
                NoChangesException.class,
                () -> updateUserUseCase.update(userData.getUserToUpdateNoChanges())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_CHANGES);

        verify(userRepository).findById(any(Long.class));
        verify(passwordEncoder, never()).encode(any(CharSequence.class));
        verify(userRepository, never()).update(any(User.class));
    }

    @Test
    void updateUserDuplicatedException() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userData.getUserActive()));
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(userData.getUserActive()));

        DuplicatedException exception = assertThrows(
                DuplicatedException.class,
                () -> updateUserUseCase.update(userData.getUserToUpdateChangeEmail())
        );

        assertEquals(exception.getMessage(), errorMessages.DUPLICATED);

        verify(userRepository).findByEmail(any(String.class));
        verify(userRepository, never()).update(any(User.class));
    }

}