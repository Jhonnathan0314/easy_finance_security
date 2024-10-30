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
        if(!accountHasUser.isValid(accountHasUser)) throw new InvalidBodyException(errorMessages.INVALID_BODY);
        logger.info("ACCION CREATE ACCOUNT_HAS_ROLE -> Body validado con exito");

        Optional<Account> accountOpt = accountRepository.findById(accountHasUser.getId().getIdAccount());
        if(accountOpt.isEmpty()) throw new NonExistenceException(errorMessages.NON_EXISTENT_DATA);
        logger.info("ACCION UPDATE ACCOUNT_HAS_ROLE -> Validacion cuenta existente con exito");

        accountHasUser.setIdAccount(accountOpt.get());

        Optional<User> userOpt = userRepository.findById(accountHasUser.getId().getIdUser());
        if(userOpt.isEmpty()) throw new NonExistenceException(errorMessages.NON_EXISTENT_DATA);
        logger.info("ACCION UPDATE ACCOUNT_HAS_ROLE -> Validacion usuario existente con exito");

        accountHasUser.setIdUser(userOpt.get());

        Optional<AccountHasUser> accountHasUserOpt = accountHasUserRepository.findById(accountHasUser.getId());
        if(accountHasUserOpt.isPresent()) throw new DuplicatedException(errorMessages.DUPLICATED);
        logger.info("ACCION CREATE ACCOUNT_HAS_ROLE -> Validacion no duplicado exitosa");

        return accountHasUserRepository.create(accountHasUser);
    }

}
