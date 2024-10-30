package com.easy.finance.context.account_has_user.infrastructure.persistence;

import com.easy.finance.context.account_has_user.domain.model.AccountHasUserId;
import com.easy.finance.utils.constants.StateEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountHasUserJpaRepository extends JpaRepository<AccountHasUserEntity, AccountHasUserId> {

    List<AccountHasUserEntity> findById_IdAccount(Long idAccount);
    List<AccountHasUserEntity> findById_IdUser(Long idUser);
    List<AccountHasUserEntity> findByState(StateEnum state);
    Optional<AccountHasUserEntity> findById_IdAccountAndId_IdUser(Long idAccount, Long idUser);
    void deleteById_IdAccountAndId_IdUser(Long idAccount, Long idUser);

}
