package com.easy.finance.context.account_has_user.application.usecase;

import com.easy.finance.context.account.domain.model.Account;
import com.easy.finance.context.account.domain.port.AccountRepository;
import com.easy.finance.context.account_has_user.domain.model.AccountHasUser;
import com.easy.finance.context.account_has_user.domain.port.AccountHasUserRepository;
import com.easy.finance.context.user.domain.model.User;
import com.easy.finance.context.user.domain.port.UserRepository;
import com.easy.finance.utils.constants.ErrorMessages;
import com.easy.finance.utils.exceptions.DuplicatedException;
import com.easy.finance.utils.exceptions.InvalidBodyException;
import com.easy.finance.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class CreateAccountHasUserUseCase {

    private final Logger logger = Logger.getLogger(CreateAccountHasUserUseCase.class.getName());

    private final AccountHasUserRepository accountHasUserRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public AccountHasUser create(AccountHasUser accountHasUser) throws NonExistenceException, DuplicatedException, InvalidBodyException {
        logger.info("Ingreso caso de uso create AccountHasUser");
        if(!accountHasUser.isValid(accountHasUser)) throw new InvalidBodyException(errorMessages.INVALID_BODY);

        logger.info("AccountHasUser create: Paso validacion invalid body");
        Optional<Account> accountOpt = accountRepository.findById(accountHasUser.getId().getIdAccount());
        if(accountOpt.isEmpty()) throw new NonExistenceException(errorMessages.NON_EXISTENT_DATA);

        accountHasUser.setIdAccount(accountOpt.get());

        logger.info("AccountHasUser create: Paso validacion account");
        Optional<User> userOpt = userRepository.findById(accountHasUser.getId().getIdUser());
        if(userOpt.isEmpty()) throw new NonExistenceException(errorMessages.NON_EXISTENT_DATA);

        accountHasUser.setIdUser(userOpt.get());

        logger.info("AccountHasUser create: Paso validacion user");
        Optional<AccountHasUser> accountHasUserOpt = accountHasUserRepository.findById(accountHasUser.getId());
        if(accountHasUserOpt.isPresent()) throw new DuplicatedException(errorMessages.DUPLICATED);

        logger.info("AccountHasUser create: Paso validacion duplicated");
        logger.info("Envio peticion create: " + accountHasUser);
        return accountHasUserRepository.create(accountHasUser);
    }

}
