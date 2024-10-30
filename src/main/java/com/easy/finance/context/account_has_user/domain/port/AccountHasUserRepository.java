package com.easy.finance.context.account_has_user.domain.port;

import com.easy.finance.context.account_has_user.domain.model.AccountHasUser;
import com.easy.finance.context.account_has_user.domain.model.AccountHasUserId;
import com.easy.finance.utils.constants.StateEnum;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountHasUserRepository {

    List<AccountHasUser> findByIdAccount(Long idAccount);
    List<AccountHasUser> findByIdUser(Long idUser);
    Optional<AccountHasUser> findById(AccountHasUserId accountHasUserId);
    List<AccountHasUser> findByState(StateEnum state);
    AccountHasUser create(AccountHasUser accountHasUser);
    void deleteById(AccountHasUserId accountHasUserId);

}
