package com.easy.finance.context.account.application.usecase;

import com.easy.finance.context.account.domain.model.Account;
import com.easy.finance.context.account.domain.port.AccountRepository;
import com.easy.finance.utils.constants.ErrorMessages;
import com.easy.finance.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindByIdAccountUseCase {

    private final AccountRepository accountRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Account findById(Long id) throws NoResultsException {
        Optional<Account> accounts = accountRepository.findById(id);
        
        if(accounts.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);

        return accounts.get();
    }

}
