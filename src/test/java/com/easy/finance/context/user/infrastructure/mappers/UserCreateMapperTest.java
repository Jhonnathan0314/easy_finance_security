package com.easy.finance.context.user.infrastructure.mappers;

import com.easy.finance.context.user.application.dto.UserCreateDto;
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
class UserCreateMapperTest {

    @InjectMocks
    private UserCreateMapper userCreateMapper;

    private static UserData userData;

    @BeforeAll
    static void setUp() {
        userData = new UserData();
    }

    @Test
    void entityToModelTest() {
        UserEntity userEntity = userData.getUserEntity();

        User userModel = userCreateMapper.entityToModel(userEntity);

        assertNotNull(userModel);
    }

    @Test
    void modelToEntityTest() {
        User userModel = userData.getUserModel();

        UserEntity userEntity = userCreateMapper.modelToEntity(userModel);

        assertNotNull(userEntity);
    }

    @Test
    void modelToDtoTest() {
        User userModel = userData.getUserModel();

        UserCreateDto userCreateDTO = userCreateMapper.modelToDto(userModel);

        assertNotNull(userCreateDTO);
    }

    @Test
    void dtoToModelTest() {
        UserCreateDto userCreateDTO = userData.getUserCreateDTO();

        User userModel = userCreateMapper.dtoToModel(userCreateDTO);

        assertNotNull(userModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<UserEntity> userEntities = List.of(userData.getUserEntity());

        List<User> userModels = userCreateMapper.entitiesToModels(userEntities);

        assertNotNull(userModels);
        assertEquals(userEntities.size(), userModels.size());
    }

    @Test
    void modelsToEntitiesTest() {
        List<User> userModels = List.of(userData.getUserModel());

        List<UserEntity> userEntities = userCreateMapper.modelsToEntities(userModels);

        assertNotNull(userEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<User> userModels = List.of(userData.getUserModel());

        List<UserCreateDto> userCreateDTOs = userCreateMapper.modelsToDtos(userModels);

        assertNotNull(userCreateDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<UserCreateDto> userCreateDTOs = List.of(userData.getUserCreateDTO());

        List<User> userModels = userCreateMapper.dtosToModels(userCreateDTOs);

        assertNotNull(userModels);
    }

}