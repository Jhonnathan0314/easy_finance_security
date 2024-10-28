package com.easy.finance.context.role.infrastructure.mappers;

import com.easy.finance.context.role.application.dto.RoleCreateDto;
import com.easy.finance.context.role.domain.model.Role;
import com.easy.finance.context.role.infrastructure.persistence.RoleEntity;
import com.easy.finance.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class RoleCreateMapper implements Mapper<RoleEntity, Role, RoleCreateDto> {

    @Override
    public Role entityToModel(RoleEntity entity) {
        return Role.builder()
                .name(entity.getName())
                .build();
    }

    @Override
    public RoleEntity modelToEntity(Role model) {
        return RoleEntity.builder()
                .name(model.getName())
                .build();
    }

    @Override
    public RoleCreateDto modelToDto(Role model) {
        return RoleCreateDto.builder()
                .name(model.getName())
                .build();
    }

    @Override
    public Role dtoToModel(RoleCreateDto dto) {
        return Role.builder()
                .name(dto.getName())
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
    public List<RoleCreateDto> modelsToDtos(List<Role> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Role> dtosToModels(List<RoleCreateDto> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }
}
