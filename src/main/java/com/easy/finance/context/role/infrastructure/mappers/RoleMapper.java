package com.easy.finance.context.role.infrastructure.mappers;

import com.easy.finance.context.role.application.dto.RoleDto;
import com.easy.finance.context.role.domain.model.Role;
import com.easy.finance.context.role.infrastructure.persistence.RoleEntity;
import com.easy.finance.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class RoleMapper implements Mapper<RoleEntity, Role, RoleDto> {

    @Override
    public Role entityToModel(RoleEntity entity) {
        return Role.builder()
                .id(entity.getId())
                .name(entity.getName())
                .state(entity.getState())
                .build();
    }

    @Override
    public RoleEntity modelToEntity(Role model) {
        return RoleEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .state(model.getState())
                .build();
    }

    @Override
    public RoleDto modelToDto(Role model) {
        return RoleDto.builder()
                .id(model.getId())
                .name(model.getName())
                .state(model.getState())
                .build();
    }

    @Override
    public Role dtoToModel(RoleDto dto) {
        return Role.builder()
                .id(dto.getId())
                .name(dto.getName())
                .state(dto.getState())
                .build();
    }

    @Override
    public List<Role> entitiesToModels(List<RoleEntity> entities) {
        return entities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleEntity> modelsToEntities(List<Role> models) {
        return models.stream()
                .map(this::modelToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleDto> modelsToDtos(List<Role> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Role> dtosToModels(List<RoleDto> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }
}
