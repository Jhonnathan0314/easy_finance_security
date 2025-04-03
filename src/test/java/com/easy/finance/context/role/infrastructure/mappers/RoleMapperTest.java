package com.easy.finance.context.role.infrastructure.mappers;

import com.easy.finance.context.role.application.dto.RoleDto;
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
class RoleMapperTest {

    @InjectMocks
    private RoleMapper roleMapper;

    private static RoleData roleData;

    @BeforeAll
    static void setUp() {
        roleData = new RoleData();
    }

    @Test
    void entityToModelTest() {
        RoleEntity roleEntity = roleData.getRoleEntity();

        Role roleModel = roleMapper.entityToModel(roleEntity);

        assertNotNull(roleModel);
    }

    @Test
    void modelToEntityTest() {
        Role roleModel = roleData.getRoleModel();

        RoleEntity roleEntity = roleMapper.modelToEntity(roleModel);

        assertNotNull(roleEntity);
    }

    @Test
    void modelToDtoTest() {
        Role roleModel = roleData.getRoleModel();

        RoleDto roleDTO = roleMapper.modelToDto(roleModel);

        assertNotNull(roleDTO);
    }

    @Test
    void dtoToModelTest() {
        RoleDto roleDTO = roleData.getRoleDTO();

        Role roleModel = roleMapper.dtoToModel(roleDTO);

        assertNotNull(roleModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<RoleEntity> roleEntities = List.of(roleData.getRoleEntity());

        List<Role> roleModels = roleMapper.entitiesToModels(roleEntities);

        assertNotNull(roleModels);
        assertEquals(roleEntities.size(), roleModels.size());
    }

    @Test
    void modelsToEntitiesTest() {
        List<Role> roleModels = List.of(roleData.getRoleModel());

        List<RoleEntity> roleEntities = roleMapper.modelsToEntities(roleModels);

        assertNotNull(roleEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<Role> roleModels = List.of(roleData.getRoleModel());

        List<RoleDto> roleDTOs = roleMapper.modelsToDtos(roleModels);

        assertNotNull(roleDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<RoleDto> roleDTOs = List.of(roleData.getRoleDTO());

        List<Role> roleModels = roleMapper.dtosToModels(roleDTOs);

        assertNotNull(roleModels);
    }

}