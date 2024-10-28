package com.easy.finance.context.role.infrastructure.mappers;

import com.easy.finance.context.role.application.dto.RoleUpdateDto;
import com.easy.finance.context.role.domain.model.Role;
import com.easy.finance.context.role.infrastructure.persistence.RoleEntity;
import com.easy.finance.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class RoleUpdateMapper implements Mapper<RoleEntity, Role, RoleUpdateDto> {

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
    public RoleUpdateDto modelToDto(Role model) {
        return RoleUpdateDto.builder()
                .id(model.getId())
                .name(model.getName())
                .state(model.getState())
                .build();
    }

    @Override
    public Role dtoToModel(RoleUpdateDto dto) {
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
    public List<RoleUpdateDto> modelsToDtos(List<Role> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Role> dtosToModels(List<RoleUpdateDto> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }
}
