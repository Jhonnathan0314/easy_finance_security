package com.easy.finance.context.account_has_user.infrastructure.mappers;

import com.easy.finance.context.account.infrastructure.mappers.AccountMapper;
import com.easy.finance.context.account_has_user.application.dto.AccountHasUserCreateDto;
import com.easy.finance.context.account_has_user.domain.model.AccountHasUser;
import com.easy.finance.context.account_has_user.infrastructure.persistence.AccountHasUserEntity;
import com.easy.finance.context.user.infrastructure.mappers.UserMapper;
import com.easy.finance.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class AccountHasUserCreateMapper implements Mapper<AccountHasUserEntity, AccountHasUser, AccountHasUserCreateDto> {

    private final AccountMapper accountMapper = new AccountMapper();
    private final UserMapper userMapper = new UserMapper();

    @Override
    public AccountHasUser entityToModel(AccountHasUserEntity entity) {
        return AccountHasUser.builder()
                .id(entity.getId())
                .idAccount(accountMapper.entityToModel(entity.getIdAccount()))
                .idUser(userMapper.entityToModel(entity.getIdUser()))
                .build();
    }

    @Override
    public AccountHasUserEntity modelToEntity(AccountHasUser model) {
        return AccountHasUserEntity.builder()
                .id(model.getId())
                .idAccount(accountMapper.modelToEntity(model.getIdAccount()))
                .idUser(userMapper.modelToEntity(model.getIdUser()))
                .build();
    }

    @Override
    public AccountHasUserCreateDto modelToDto(AccountHasUser model) {
        return AccountHasUserCreateDto.builder()
                .id(model.getId())
                .build();
    }

    @Override
    public AccountHasUser dtoToModel(AccountHasUserCreateDto dto) {
        return AccountHasUser.builder()
                .id(dto.getId())
                .build();
    }

    @Override
    public List<AccountHasUser> entitiesToModels(List<AccountHasUserEntity> entities) {
        return entities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountHasUserEntity> modelsToEntities(List<AccountHasUser> models) {
        return models.stream()
                .map(this::modelToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountHasUserCreateDto> modelsToDtos(List<AccountHasUser> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountHasUser> dtosToModels(List<AccountHasUserCreateDto> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }
}
