package com.easy.finance.context.user.infrastructure.mappers;

import com.easy.finance.context.role.infrastructure.mappers.RoleMapper;
import com.easy.finance.context.user.application.dto.UserDto;
import com.easy.finance.context.user.domain.model.User;
import com.easy.finance.context.user.infrastructure.persistence.UserEntity;
import com.easy.finance.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper implements Mapper<UserEntity, User, UserDto> {

    private final RoleMapper roleMapper = new RoleMapper();

    @Override
    public User entityToModel(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .name(entity.getName())
                .lastName(entity.getLastName())
                .password(entity.getPassword())
                .role(roleMapper.entityToModel(entity.getRole()))
                .state(entity.getState())
                .build();
    }

    @Override
    public UserEntity modelToEntity(User model) {
        return UserEntity.builder()
                .id(model.getId())
                .email(model.getEmail())
                .name(model.getName())
                .lastName(model.getLastName())
                .password(model.getPassword())
                .role(roleMapper.modelToEntity(model.getRole()))
                .state(model.getState())
                .build();
    }

    @Override
    public UserDto modelToDto(User model) {
        return UserDto.builder()
                .id(model.getId())
                .email(model.getEmail())
                .name(model.getName())
                .lastName(model.getLastName())
                .password(model.getPassword())
                .role(roleMapper.modelToDto(model.getRole()))
                .state(model.getState())
                .build();
    }

    @Override
    public User dtoToModel(UserDto dto) {
        return User.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .name(dto.getName())
                .lastName(dto.getLastName())
                .password(dto.getPassword())
                .role(roleMapper.dtoToModel(dto.getRole()))
                .state(dto.getState())
                .build();
    }

    @Override
    public List<User> entitiesToModels(List<UserEntity> entities) {
        return entities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserEntity> modelsToEntities(List<User> models) {
        return models.stream()
                .map(this::modelToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> modelsToDtos(List<User> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> dtosToModels(List<UserDto> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }
}
