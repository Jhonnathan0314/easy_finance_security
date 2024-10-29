package com.easy.finance.context.account.domain.port;

import com.easy.finance.context.account.domain.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {

    List<Account> findAll();
    Optional<Account> findById(Long id);
    Account create(Account account);
    Account update(Account account);
    void deleteById(Long id);

}
