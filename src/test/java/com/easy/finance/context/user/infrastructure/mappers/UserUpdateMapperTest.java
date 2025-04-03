package com.easy.finance.context.user.infrastructure.mappers;

import com.easy.finance.context.user.application.dto.UserUpdateDto;
import com.easy.finance.context.user.data.UserData;
import com.easy.finance.context.user.domain.model.User;
import com.easy.finance.context.user.infrastructure.persistence.UserEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserUpdateMapperTest {

    @InjectMocks
    private UserUpdateMapper userUpdateMapper;

    private static UserData userData;

    @BeforeAll
    static void setUp() {
        userData = new UserData();
    }

    @Test
    void entityToModelTest() {
        UserEntity userEntity = userData.getUserEntity();

        User userModel = userUpdateMapper.entityToModel(userEntity);

        assertNotNull(userModel);
    }

    @Test
    void modelToEntityTest() {
        User userModel = userData.getUserModel();

        UserEntity userEntity = userUpdateMapper.modelToEntity(userModel);

        assertNotNull(userEntity);
    }

    @Test
    void modelToDtoTest() {
        User userModel = userData.getUserModel();

        UserUpdateDto userUpdateDTO = userUpdateMapper.modelToDto(userModel);

        assertNotNull(userUpdateDTO);
    }

    @Test
    void dtoToModelTest() {
        UserUpdateDto userUpdateDTO = userData.getUserUpdateDTO();

        User userModel = userUpdateMapper.dtoToModel(userUpdateDTO);

        assertNotNull(userModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<UserEntity> userEntities = List.of(userData.getUserEntity());

        List<User> userModels = userUpdateMapper.entitiesToModels(userEntities);

        assertNotNull(userModels);
        assertEquals(userEntities.size(), userModels.size());
    }

    @Test
    void modelsToEntitiesTest() {
        List<User> userModels = List.of(userData.getUserModel());

        List<UserEntity> userEntities = userUpdateMapper.modelsToEntities(userModels);

        assertNotNull(userEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<User> userModels = List.of(userData.getUserModel());

        List<UserUpdateDto> userUpdateDTOs = userUpdateMapper.modelsToDtos(userModels);

        assertNotNull(userUpdateDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<UserUpdateDto> userUpdateDTOs = List.of(userData.getUserUpdateDTO());

        List<User> userModels = userUpdateMapper.dtosToModels(userUpdateDTOs);

        assertNotNull(userModels);
    }

}