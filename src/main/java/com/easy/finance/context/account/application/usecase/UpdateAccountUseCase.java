package com.easy.finance.context.account.application.usecase;

import com.easy.finance.context.account.domain.model.Account;
import com.easy.finance.context.account.domain.port.AccountRepository;
import com.easy.finance.utils.constants.ErrorMessages;
import com.easy.finance.utils.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateAccountUseCase {

    private final AccountRepository accountRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Account update(Account account) throws InvalidBodyException, NoChangesException, NoIdReceivedException, NonExistenceException {

        if(account.getId() == null) throw new NoIdReceivedException(errorMessages.NO_ID_RECEIVED);

        if(!account.isValid(account)) throw new InvalidBodyException(errorMessages.INVALID_BODY);

        Optional<Account> accountIdOpt = accountRepository.findById(account.getId());

        if(accountIdOpt.isEmpty()) throw new NonExistenceException(errorMessages.NON_EXISTENT_DATA);

        if(account.equals(accountIdOpt.get())) throw new NoChangesException(errorMessages.NO_CHANGES);

        return accountRepository.update(account);
    }

}
