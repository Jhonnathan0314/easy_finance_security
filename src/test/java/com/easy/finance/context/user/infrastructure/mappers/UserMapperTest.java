package com.easy.finance.context.user.infrastructure.mappers;

import com.easy.finance.context.user.application.dto.UserDto;
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
class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;

    private static UserData userData;

    @BeforeAll
    static void setUp() {
        userData = new UserData();
    }

    @Test
    void entityToModelTest() {
        UserEntity userEntity = userData.getUserEntity();

        User userModel = userMapper.entityToModel(userEntity);

        assertNotNull(userModel);
    }

    @Test
    void modelToEntityTest() {
        User userModel = userData.getUserModel();

        UserEntity userEntity = userMapper.modelToEntity(userModel);

        assertNotNull(userEntity);
    }

    @Test
    void modelToDtoTest() {
        User userModel = userData.getUserModel();

        UserDto userDTO = userMapper.modelToDto(userModel);

        assertNotNull(userDTO);
    }

    @Test
    void dtoToModelTest() {
        UserDto userDTO = userData.getUserDTO();

        User userModel = userMapper.dtoToModel(userDTO);

        assertNotNull(userModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<UserEntity> userEntities = List.of(userData.getUserEntity());

        List<User> userModels = userMapper.entitiesToModels(userEntities);

        assertNotNull(userModels);
        assertEquals(userEntities.size(), userModels.size());
    }

    @Test
    void modelsToEntitiesTest() {
        List<User> userModels = List.of(userData.getUserModel());

        List<UserEntity> userEntities = userMapper.modelsToEntities(userModels);

        assertNotNull(userEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<User> userModels = List.of(userData.getUserModel());

        List<UserDto> userDTOs = userMapper.modelsToDtos(userModels);

        assertNotNull(userDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<UserDto> userDTOs = List.of(userData.getUserDTO());

        List<User> userModels = userMapper.dtosToModels(userDTOs);

        assertNotNull(userModels);
    }

}