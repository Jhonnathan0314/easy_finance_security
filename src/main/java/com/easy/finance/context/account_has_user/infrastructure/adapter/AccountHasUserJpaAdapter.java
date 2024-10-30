package com.easy.finance.context.account_has_user.infrastructure.adapter;

import com.easy.finance.context.account_has_user.domain.model.AccountHasUser;
import com.easy.finance.context.account_has_user.domain.model.AccountHasUserId;
import com.easy.finance.context.account_has_user.domain.port.AccountHasUserRepository;
import com.easy.finance.context.account_has_user.infrastructure.mappers.AccountHasUserCreateMapper;
import com.easy.finance.context.account_has_user.infrastructure.mappers.AccountHasUserMapper;
import com.easy.finance.context.account_has_user.infrastructure.persistence.AccountHasUserEntity;
import com.easy.finance.context.account_has_user.infrastructure.persistence.AccountHasUserJpaRepository;
import com.easy.finance.utils.constants.StateEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Repository
@RequiredArgsConstructor
public class AccountHasUserJpaAdapter implements AccountHasUserRepository {

    private final Logger logger = Logger.getLogger(AccountHasUserJpaAdapter.class.getName());

    private final AccountHasUserJpaRepository accountHasUserJpaRepository;

    private final AccountHasUserMapper accountHasUserMapper = new AccountHasUserMapper();
    private final AccountHasUserCreateMapper accountHasUserCreateMapper = new AccountHasUserCreateMapper();

    @Override
    public List<AccountHasUser> findByIdAccount(Long idAccount) {
        List<AccountHasUserEntity> entities = accountHasUserJpaRepository.findById_IdAccount(idAccount);
        return accountHasUserMapper.entitiesToModels(entities);
    }

    @Override
    public List<AccountHasUser> findByIdUser(Long idUser) {
        List<AccountHasUserEntity> entities = accountHasUserJpaRepository.findById_IdUser(idUser);
        return accountHasUserMapper.entitiesToModels(entities);
    }

    @Override
    public Optional<AccountHasUser> findById(AccountHasUserId accountHasUserId) {
        Optional<AccountHasUserEntity> entities = accountHasUserJpaRepository.findById_IdAccountAndId_IdUser(accountHasUserId.getIdAccount(), accountHasUserId.getIdUser());
        return entities.map(accountHasUserMapper::entityToModel);
    }

    @Override
    public List<AccountHasUser> findByState(StateEnum state) {
        List<AccountHasUserEntity> entities = accountHasUserJpaRepository.findByState(state);
        return accountHasUserMapper.entitiesToModels(entities);
    }

    @Override
    public AccountHasUser create(AccountHasUser accountHasUser) {
        logger.info("recibo model para jpa: " + accountHasUser.toString());
        logger.info("transformo a entity para jpa: " + accountHasUserCreateMapper.modelToEntity(accountHasUser).toString());
        AccountHasUserEntity entity = accountHasUserJpaRepository.save(accountHasUserCreateMapper.modelToEntity(accountHasUser));
        return accountHasUserMapper.entityToModel(entity);
    }

    @Override
    public void deleteById(AccountHasUserId accountHasUserId) {
        accountHasUserJpaRepository.deleteById_IdAccountAndId_IdUser(accountHasUserId.getIdAccount(), accountHasUserId.getIdUser());
    }
}
