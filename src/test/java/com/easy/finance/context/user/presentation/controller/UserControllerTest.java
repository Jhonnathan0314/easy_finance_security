package com.easy.finance.context.user.presentation.controller;

import com.easy.finance.context.user.application.dto.UserCreateDto;
import com.easy.finance.context.user.application.dto.UserUpdateDto;
import com.easy.finance.context.user.application.usecase.*;
import com.easy.finance.context.user.data.UserData;
import com.easy.finance.context.user.domain.model.User;
import com.easy.finance.context.user.infrastructure.mappers.UserCreateMapper;
import com.easy.finance.context.user.infrastructure.mappers.UserUpdateMapper;
import com.easy.finance.general.GeneralData;
import com.easy.finance.utils.constants.ErrorMessages;
import com.easy.finance.utils.exceptions.*;
import com.easy.finance.utils.messages.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Mock
    private FindAllUserUseCase findAllUserUseCase;

    @Mock
    private FindByIdUserUseCase findByIdUserUseCase;

    @Mock
    private CreateUserUseCase createUserUseCase;

    @Mock
    private UpdateUserUseCase updateUserUseCase;

    @Mock
    private DeleteByIdUserUseCase deleteByIdUserUseCase;

    @Mock
    private UserCreateMapper userCreateMapper;

    @Mock
    private UserUpdateMapper userUpdateMapper;

    private ErrorMessages errorMessages;
    private UserData userData;
    private GeneralData generalData;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        userData = new UserData();
        generalData = new GeneralData();
        errorMessages = new ErrorMessages();
        userCreateMapper = new UserCreateMapper();
        userUpdateMapper = new UserUpdateMapper();
    }

    @Test
    @Order(0)
    void findAllSuccess() throws Exception {
        when(findAllUserUseCase.findAll()).thenReturn(userData.getUsersList());

        mockMvc.perform(get("/api/v1/security/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty());

        verify(findAllUserUseCase).findAll();
    }

    @Test
    @Order(1)
    void findAllFailedNoResultsException() throws Exception {
        ApiResponse<List<User>> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoResults());

        when(findAllUserUseCase.findAll())
                .thenThrow(new NoResultsException(errorMessages.NO_RESULTS));

        mockMvc.perform(get("/api/v1/security/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(findAllUserUseCase).findAll();
    }

    @Test
    @Order(2)
    void findByIdSuccess() throws Exception {
        when(findByIdUserUseCase.findById(any(Long.class))).thenReturn(userData.getUserResponseOne());

        mockMvc.perform(get("/api/v1/security/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").value(userData.getUserResponseOne()));

        verify(findByIdUserUseCase).findById(any(Long.class));
    }

    @Test
    @Order(3)
    void findByIdFailedNoResultsException() throws Exception {
        ApiResponse<User> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoResults());

        when(findByIdUserUseCase.findById(any(Long.class)))
                .thenThrow(new NoResultsException(errorMessages.NO_RESULTS));

        mockMvc.perform(get("/api/v1/security/user/12345")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(findByIdUserUseCase).findById(any(Long.class));
    }

    @Test
    @Order(4)
    void createSuccess() throws Exception {
        when(createUserUseCase.create(any(User.class))).thenReturn(userData.getUserResponseOne());

        UserCreateDto body = userCreateMapper.modelToDto(userData.getUserCreateValid());

        mockMvc.perform(post("/api/v1/security/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").value(userData.getUserResponseOne()));

        verify(createUserUseCase).create(any(User.class));
    }

    @Test
    @Order(5)
    void createFailedDuplicatedException() throws Exception {
        ApiResponse<User> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorDuplicated());

        when(createUserUseCase.create(any(User.class)))
                .thenThrow(new DuplicatedException(errorMessages.DUPLICATED));

        UserCreateDto body = userCreateMapper.modelToDto(userData.getUserCreateValid());

        mockMvc.perform(post("/api/v1/security/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(createUserUseCase).create(any(User.class));
    }

    @Test
    @Order(6)
    void createFailedInvalidBodyException() throws Exception {
        ApiResponse<User> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorInvalidBody());

        when(createUserUseCase.create(any(User.class)))
                .thenThrow(new InvalidBodyException(errorMessages.INVALID_BODY));

        UserCreateDto body = userCreateMapper.modelToDto(userData.getUserCreateValid());

        mockMvc.perform(post("/api/v1/security/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(createUserUseCase).create(any(User.class));
    }

    @Test
    @Order(7)
    void updateSuccess() throws Exception {
        when(updateUserUseCase.update(any(User.class))).thenReturn(userData.getUserResponseOne());

        UserUpdateDto body = userUpdateMapper.modelToDto(userData.getUserToUpdate());

        mockMvc.perform(put("/api/v1/security/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").value(userData.getUserResponseOne()));

        verify(updateUserUseCase).update(any(User.class));
    }

    @Test
    @Order(8)
    void updateFailedNoIdReceivedException() throws Exception {
        ApiResponse<User> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoIdReceived());

        when(updateUserUseCase.update(any(User.class)))
                .thenThrow(new NoIdReceivedException(errorMessages.NO_ID_RECEIVED));

        UserUpdateDto body = userUpdateMapper.modelToDto(userData.getUserToUpdateNoId());

        mockMvc.perform(put("/api/v1/security/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(updateUserUseCase).update(any(User.class));
    }

    @Test
    @Order(9)
    void updateFailedInvalidBodyException() throws Exception {
        ApiResponse<User> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorInvalidBody());

        when(updateUserUseCase.update(any(User.class)))
                .thenThrow(new InvalidBodyException(errorMessages.INVALID_BODY));

        UserUpdateDto body = userUpdateMapper.modelToDto(userData.getUserToUpdateInvalid());

        mockMvc.perform(put("/api/v1/security/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(updateUserUseCase).update(any(User.class));
    }

    @Test
    @Order(10)
    void updateFailedNoResultsException() throws Exception {
        ApiResponse<User> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNonExistence());

        when(updateUserUseCase.update(any(User.class)))
                .thenThrow(new NonExistenceException(errorMessages.NON_EXISTENT_DATA));

        UserUpdateDto body = userUpdateMapper.modelToDto(userData.getUserInactive());

        mockMvc.perform(put("/api/v1/security/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(updateUserUseCase).update(any(User.class));
    }

    @Test
    @Order(11)
    void updateFailedNoChangesException() throws Exception {
        ApiResponse<User> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoChanges());

        when(updateUserUseCase.update(any(User.class)))
                .thenThrow(new NoChangesException(errorMessages.NO_CHANGES));

        UserUpdateDto body = userUpdateMapper.modelToDto(userData.getUserToUpdate());

        mockMvc.perform(put("/api/v1/security/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body)))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(updateUserUseCase).update(any(User.class));
    }

    @Test
    @Order(12)
    void deleteByIdSuccess() throws Exception {
        mockMvc.perform(delete("/api/v1/security/user/1"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").doesNotExist());

        verify(deleteByIdUserUseCase).deleteById(any(Long.class));
    }

    @Test
    @Order(13)
    void deleteByIdFailedNonExistenceException() throws Exception {
        ApiResponse<User> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNonExistence());

        doThrow(new NonExistenceException(errorMessages.NON_EXISTENT_DATA))
                .when(deleteByIdUserUseCase).deleteById(any(Long.class));

        mockMvc.perform(delete("/api/v1/security/user/12345"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(deleteByIdUserUseCase).deleteById(any(Long.class));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}