package com.easy.finance.context.account.infrastructure.mappers;

import com.easy.finance.context.account.application.dto.AccountUpdateDto;
import com.easy.finance.context.account.domain.model.Account;
import com.easy.finance.context.account.infrastructure.persistence.AccountEntity;
import com.easy.finance.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class AccountUpdateMapper implements Mapper<AccountEntity, Account, AccountUpdateDto> {

    @Override
    public Account entityToModel(AccountEntity entity) {
        return Account.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .state(entity.getState())
                .build();
    }

    @Override
    public AccountEntity modelToEntity(Account model) {
        return AccountEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .state(model.getState())
                .build();
    }

    @Override
    public AccountUpdateDto modelToDto(Account model) {
        return AccountUpdateDto.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .state(model.getState())
                .build();
    }

    @Override
    public Account dtoToModel(AccountUpdateDto dto) {
        return Account.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .state(dto.getState())
                .build();
    }

    @Override
    public List<Account> entitiesToModels(List<AccountEntity> entities) {
        return entities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountEntity> modelsToEntities(List<Account> models) {
        return models.stream()
                .map(this::modelToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountUpdateDto> modelsToDtos(List<Account> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> dtosToModels(List<AccountUpdateDto> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }
}
