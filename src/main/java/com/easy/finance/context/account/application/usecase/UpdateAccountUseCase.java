package com.easy.finance.context.account.application.usecase;

import com.easy.finance.context.account.domain.model.Account;
import com.easy.finance.context.account.domain.port.AccountRepository;
import com.easy.finance.utils.constants.ErrorMessages;
import com.easy.finance.utils.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class UpdateAccountUseCase {

    private final Logger logger = Logger.getLogger(UpdateAccountUseCase.class.getName());

    private final AccountRepository accountRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Account update(Account account) throws InvalidBodyException, NoChangesException, NoIdReceivedException, NonExistenceException {

        if(account.getId() == null) throw new NoIdReceivedException(errorMessages.NO_ID_RECEIVED);
        logger.info("ACCION UPDATE ACCOUNT -> Id validado con exito");

        if(!account.isValid(account)) throw new InvalidBodyException(errorMessages.INVALID_BODY);
        logger.info("ACCION UPDATE ACCOUNT -> Body validado con exito");

        Optional<Account> accountIdOpt = accountRepository.findById(account.getId());

        if(accountIdOpt.isEmpty()) throw new NonExistenceException(errorMessages.NON_EXISTENT_DATA);
        logger.info("ACCION UPDATE ACCOUNT -> Validacion cuenta existente con exito");

        if(account.equals(accountIdOpt.get())) throw new NoChangesException(errorMessages.NO_CHANGES);
        logger.info("ACCION UPDATE ACCOUNT -> Validacion cambios a aplicar con exito");

        return accountRepository.update(account);
    }

}
