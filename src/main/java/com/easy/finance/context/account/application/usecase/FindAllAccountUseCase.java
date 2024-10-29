package com.easy.finance.context.account.application.usecase;

import com.easy.finance.context.account.domain.model.Account;
import com.easy.finance.context.account.domain.port.AccountRepository;
import com.easy.finance.utils.constants.ErrorMessages;
import com.easy.finance.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllAccountUseCase {

    private final AccountRepository accountRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public List<Account> findAll() throws NoResultsException {
        List<Account> accounts = accountRepository.findAll();

        if(accounts.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);

        return accounts;
    }

}