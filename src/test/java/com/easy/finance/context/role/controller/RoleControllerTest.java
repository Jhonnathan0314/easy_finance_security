package com.easy.finance.context.role.controller;

import com.easy.finance.context.role.application.dto.RoleCreateDto;
import com.easy.finance.context.role.application.dto.RoleUpdateDto;
import com.easy.finance.context.role.application.usecase.*;
import com.easy.finance.context.role.data.RoleData;
import com.easy.finance.context.role.domain.model.Role;
import com.easy.finance.context.role.infrastructure.mappers.RoleCreateMapper;
import com.easy.finance.context.role.infrastructure.mappers.RoleUpdateMapper;
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
class RoleControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private RoleController roleController;

    @Mock
    private FindAllRoleUseCase findAllRoleUseCase;

    @Mock
    private FindByIdRoleUseCase findByIdRoleUseCase;

    @Mock
    private CreateRoleUseCase createRoleUseCase;

    @Mock
    private UpdateRoleUseCase updateRoleUseCase;

    @Mock
    private DeleteByIdRoleUseCase deleteByIdRoleUseCase;

    @Mock
    private RoleCreateMapper roleCreateMapper;

    @Mock
    private RoleUpdateMapper roleUpdateMapper;

    private ErrorMessages errorMessages;
    private RoleData roleData;
    private GeneralData generalData;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
        roleData = new RoleData();
        generalData = new GeneralData();
        errorMessages = new ErrorMessages();
        roleCreateMapper = new RoleCreateMapper();
        roleUpdateMapper = new RoleUpdateMapper();
    }

    @Test
    @Order(0)
    void findAllSuccess() throws Exception {
        when(findAllRoleUseCase.findAll()).thenReturn(roleData.getRolesList());

        mockMvc.perform(get("/api/v1/security/role")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty());

        verify(findAllRoleUseCase).findAll();
    }

    @Test
    @Order(1)
    void findAllFailedNoResultsException() throws Exception {
        ApiResponse<List<Role>> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoResults());

        when(findAllRoleUseCase.findAll())
                .thenThrow(new NoResultsException(errorMessages.NO_RESULTS));

        mockMvc.perform(get("/api/v1/security/role")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(findAllRoleUseCase).findAll();
    }

    @Test
    @Order(2)
    void findByIdSuccess() throws Exception {
        when(findByIdRoleUseCase.findById(any(Long.class))).thenReturn(roleData.getRoleResponseOne());

        mockMvc.perform(get("/api/v1/security/role/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").value(roleData.getRoleResponseOne()));

        verify(findByIdRoleUseCase).findById(any(Long.class));
    }

    @Test
    @Order(3)
    void findByIdFailedNoResultsException() throws Exception {
        ApiResponse<Role> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoResults());

        when(findByIdRoleUseCase.findById(any(Long.class)))
                .thenThrow(new NoResultsException(errorMessages.NO_RESULTS));

        mockMvc.perform(get("/api/v1/security/role/12345")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(findByIdRoleUseCase).findById(any(Long.class));
    }

    @Test
    @Order(4)
    void createSuccess() throws Exception {
        when(createRoleUseCase.create(any(Role.class))).thenReturn(roleData.getRoleResponseOne());

        RoleCreateDto body = roleCreateMapper.modelToDto(roleData.getRoleCreateValid());

        mockMvc.perform(post("/api/v1/security/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").value(roleData.getRoleResponseOne()));

        verify(createRoleUseCase).create(any(Role.class));
    }

    @Test
    @Order(5)
    void createFailedDuplicatedException() throws Exception {
        ApiResponse<Role> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorDuplicated());

        when(createRoleUseCase.create(any(Role.class)))
                .thenThrow(new DuplicatedException(errorMessages.DUPLICATED));

        RoleCreateDto body = roleCreateMapper.modelToDto(roleData.getRoleCreateValid());

        mockMvc.perform(post("/api/v1/security/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(createRoleUseCase).create(any(Role.class));
    }

    @Test
    @Order(6)
    void createFailedInvalidBodyException() throws Exception {
        ApiResponse<Role> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorInvalidBody());

        when(createRoleUseCase.create(any(Role.class)))
                .thenThrow(new InvalidBodyException(errorMessages.INVALID_BODY));

        RoleCreateDto body = roleCreateMapper.modelToDto(roleData.getRoleCreateValid());

        mockMvc.perform(post("/api/v1/security/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(createRoleUseCase).create(any(Role.class));
    }

    @Test
    @Order(7)
    void updateSuccess() throws Exception {
        when(updateRoleUseCase.update(any(Role.class))).thenReturn(roleData.getRoleResponseOne());

        RoleUpdateDto body = roleUpdateMapper.modelToDto(roleData.getRoleToUpdate());

        mockMvc.perform(put("/api/v1/security/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").value(roleData.getRoleResponseOne()));

        verify(updateRoleUseCase).update(any(Role.class));
    }

    @Test
    @Order(8)
    void updateFailedNoIdReceivedException() throws Exception {
        ApiResponse<Role> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoIdReceived());

        when(updateRoleUseCase.update(any(Role.class)))
                .thenThrow(new NoIdReceivedException(errorMessages.NO_ID_RECEIVED));

        RoleUpdateDto body = roleUpdateMapper.modelToDto(roleData.getRoleToUpdateNoId());

        mockMvc.perform(put("/api/v1/security/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(updateRoleUseCase).update(any(Role.class));
    }

    @Test
    @Order(9)
    void updateFailedInvalidBodyException() throws Exception {
        ApiResponse<Role> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorInvalidBody());

        when(updateRoleUseCase.update(any(Role.class)))
                .thenThrow(new InvalidBodyException(errorMessages.INVALID_BODY));

        RoleUpdateDto body = roleUpdateMapper.modelToDto(roleData.getRoleToUpdateInvalid());

        mockMvc.perform(put("/api/v1/security/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(updateRoleUseCase).update(any(Role.class));
    }

    @Test
    @Order(10)
    void updateFailedNoResultsException() throws Exception {
        ApiResponse<Role> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNonExistence());

        when(updateRoleUseCase.update(any(Role.class)))
                .thenThrow(new NonExistenceException(errorMessages.NON_EXISTENT_DATA));

        RoleUpdateDto body = roleUpdateMapper.modelToDto(roleData.getRoleInactive());

        mockMvc.perform(put("/api/v1/security/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(updateRoleUseCase).update(any(Role.class));
    }

    @Test
    @Order(11)
    void updateFailedNoChangesException() throws Exception {
        ApiResponse<Role> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNoChanges());

        when(updateRoleUseCase.update(any(Role.class)))
                .thenThrow(new NoChangesException(errorMessages.NO_CHANGES));

        RoleUpdateDto body = roleUpdateMapper.modelToDto(roleData.getRoleToUpdate());

        mockMvc.perform(put("/api/v1/security/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(body)))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(updateRoleUseCase).update(any(Role.class));
    }

    @Test
    @Order(12)
    void deleteByIdSuccess() throws Exception {
        mockMvc.perform(delete("/api/v1/security/role/1"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").doesNotExist());

        verify(deleteByIdRoleUseCase).deleteById(any(Long.class));
    }

    @Test
    @Order(13)
    void deleteByIdFailedNonExistenceException() throws Exception {
        ApiResponse<Role> expectedResponse = new ApiResponse<>();
        expectedResponse.setData(null);
        expectedResponse.setError(generalData.getErrorNonExistence());

        doThrow(new NonExistenceException(errorMessages.NON_EXISTENT_DATA))
                .when(deleteByIdRoleUseCase).deleteById(any(Long.class));

        mockMvc.perform(delete("/api/v1/security/role/12345"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(expectedResponse.getError().getCode()))
                .andExpect(jsonPath("$.error.title").value(expectedResponse.getError().getTitle()))
                .andExpect(jsonPath("$.error.detail").value(expectedResponse.getError().getDetail()));

        verify(deleteByIdRoleUseCase).deleteById(any(Long.class));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}