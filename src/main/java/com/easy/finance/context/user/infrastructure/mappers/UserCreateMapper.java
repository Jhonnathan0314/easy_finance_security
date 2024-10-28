package com.easy.finance.context.user.infrastructure.mappers;

import com.easy.finance.context.user.application.dto.UserCreateDto;
import com.easy.finance.context.user.domain.model.User;
import com.easy.finance.context.user.infrastructure.persistence.UserEntity;
import com.easy.finance.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class UserCreateMapper implements Mapper<UserEntity, User, UserCreateDto> {

    @Override
    public User entityToModel(UserEntity entity) {
        return User.builder()
                .email(entity.getEmail())
                .name(entity.getName())
                .lastName(entity.getLastName())
                .password(entity.getPassword())
                .build();
    }

    @Override
    public UserEntity modelToEntity(User model) {
        return UserEntity.builder()
                .email(model.getEmail())
                .name(model.getName())
                .lastName(model.getLastName())
                .password(model.getPassword())
                .build();
    }

    @Override
    public UserCreateDto modelToDto(User model) {
        return UserCreateDto.builder()
                .email(model.getEmail())
                .name(model.getName())
                .lastName(model.getLastName())
                .password(model.getPassword())
                .build();
    }

    @Override
    public User dtoToModel(UserCreateDto dto) {
        return User.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .lastName(dto.getLastName())
                .password(dto.getPassword())
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
    public List<UserCreateDto> modelsToDtos(List<User> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> dtosToModels(List<UserCreateDto> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }
}
