package com.easy.finance.context.account.application.usecase;

import com.easy.finance.context.account.domain.model.Account;
import com.easy.finance.context.account.domain.port.AccountRepository;
import com.easy.finance.utils.constants.ErrorMessages;
import com.easy.finance.utils.exceptions.InvalidBodyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;


@Service
@RequiredArgsConstructor
public class CreateAccountUseCase {

    private final Logger logger = Logger.getLogger(CreateAccountUseCase.class.getName());

    private final AccountRepository accountRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Account create(Account account) throws InvalidBodyException {

        if(!account.isValid(account)) throw new InvalidBodyException(errorMessages.INVALID_BODY);
        logger.info("ACCION CREATE ACCOUNT -> Body validado con exito");

        return accountRepository.create(account);
    }

}
