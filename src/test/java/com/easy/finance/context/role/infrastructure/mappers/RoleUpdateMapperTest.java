package com.easy.finance.context.role.infrastructure.mappers;

import com.easy.finance.context.role.application.dto.RoleUpdateDto;
import com.easy.finance.context.role.data.RoleData;
import com.easy.finance.context.role.domain.model.Role;
import com.easy.finance.context.role.infrastructure.persistence.RoleEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RoleUpdateMapperTest {

    @InjectMocks
    private RoleUpdateMapper roleUpdateMapper;

    private static RoleData roleData;

    @BeforeAll
    static void setUp() {
        roleData = new RoleData();
    }

    @Test
    void entityToModelTest() {
        RoleEntity roleEntity = roleData.getRoleEntity();

        Role roleModel = roleUpdateMapper.entityToModel(roleEntity);

        assertNotNull(roleModel);
    }

    @Test
    void modelToEntityTest() {
        Role roleModel = roleData.getRoleModel();

        RoleEntity roleEntity = roleUpdateMapper.modelToEntity(roleModel);

        assertNotNull(roleEntity);
    }

    @Test
    void modelToDtoTest() {
        Role roleModel = roleData.getRoleModel();

        RoleUpdateDto roleUpdateDTO = roleUpdateMapper.modelToDto(roleModel);

        assertNotNull(roleUpdateDTO);
    }

    @Test
    void dtoToModelTest() {
        RoleUpdateDto roleUpdateDTO = roleData.getRoleUpdateDTO();

        Role roleModel = roleUpdateMapper.dtoToModel(roleUpdateDTO);

        assertNotNull(roleModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<RoleEntity> roleEntities = List.of(roleData.getRoleEntity());

        List<Role> roleModels = roleUpdateMapper.entitiesToModels(roleEntities);

        assertNotNull(roleModels);
        assertEquals(roleEntities.size(), roleModels.size());
    }

    @Test
    void modelsToEntitiesTest() {
        List<Role> roleModels = List.of(roleData.getRoleModel());

        List<RoleEntity> roleEntities = roleUpdateMapper.modelsToEntities(roleModels);

        assertNotNull(roleEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<Role> roleModels = List.of(roleData.getRoleModel());

        List<RoleUpdateDto> roleUpdateDTOs = roleUpdateMapper.modelsToDtos(roleModels);

        assertNotNull(roleUpdateDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<RoleUpdateDto> roleUpdateDTOs = List.of(roleData.getRoleUpdateDTO());

        List<Role> roleModels = roleUpdateMapper.dtosToModels(roleUpdateDTOs);

        assertNotNull(roleModels);
    }

}