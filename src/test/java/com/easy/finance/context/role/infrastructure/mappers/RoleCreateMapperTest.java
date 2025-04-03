package com.easy.finance.context.role.infrastructure.mappers;

import com.easy.finance.context.role.application.dto.RoleCreateDto;
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
class RoleCreateMapperTest {

    @InjectMocks
    private RoleCreateMapper roleCreateMapper;

    private static RoleData roleData;

    @BeforeAll
    static void setUp() {
        roleData = new RoleData();
    }

    @Test
    void entityToModelTest() {
        RoleEntity roleEntity = roleData.getRoleEntity();

        Role roleModel = roleCreateMapper.entityToModel(roleEntity);

        assertNotNull(roleModel);
    }

    @Test
    void modelToEntityTest() {
        Role roleModel = roleData.getRoleModel();

        RoleEntity roleEntity = roleCreateMapper.modelToEntity(roleModel);

        assertNotNull(roleEntity);
    }

    @Test
    void modelToDtoTest() {
        Role roleModel = roleData.getRoleModel();

        RoleCreateDto roleCreateDTO = roleCreateMapper.modelToDto(roleModel);

        assertNotNull(roleCreateDTO);
    }

    @Test
    void dtoToModelTest() {
        RoleCreateDto roleCreateDTO = roleData.getRoleCreateDTO();

        Role roleModel = roleCreateMapper.dtoToModel(roleCreateDTO);

        assertNotNull(roleModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<RoleEntity> roleEntities = List.of(roleData.getRoleEntity());

        List<Role> roleModels = roleCreateMapper.entitiesToModels(roleEntities);

        assertNotNull(roleModels);
        assertEquals(roleEntities.size(), roleModels.size());
    }

    @Test
    void modelsToEntitiesTest() {
        List<Role> roleModels = List.of(roleData.getRoleModel());

        List<RoleEntity> roleEntities = roleCreateMapper.modelsToEntities(roleModels);

        assertNotNull(roleEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<Role> roleModels = List.of(roleData.getRoleModel());

        List<RoleCreateDto> roleCreateDTOs = roleCreateMapper.modelsToDtos(roleModels);

        assertNotNull(roleCreateDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<RoleCreateDto> roleCreateDTOs = List.of(roleData.getRoleCreateDTO());

        List<Role> roleModels = roleCreateMapper.dtosToModels(roleCreateDTOs);

        assertNotNull(roleModels);
    }

}