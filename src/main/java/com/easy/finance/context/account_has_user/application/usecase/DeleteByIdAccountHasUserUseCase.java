package com.easy.finance.context.account_has_user.application.usecase;

import com.easy.finance.context.account_has_user.domain.model.AccountHasUser;
import com.easy.finance.context.account_has_user.domain.model.AccountHasUserId;
import com.easy.finance.context.account_has_user.domain.port.AccountHasUserRepository;
import com.easy.finance.utils.constants.ErrorMessages;
import com.easy.finance.utils.exceptions.NoResultsException;
import com.easy.finance.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class DeleteByIdAccountHasUserUseCase {

    private final Logger logger = Logger.getLogger(DeleteByIdAccountHasUserUseCase.class.getName());

    private final AccountHasUserRepository accountHasUserRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public void deleteById(AccountHasUserId id) throws NonExistenceException {
        Optional<AccountHasUser> accountHasUsers = accountHasUserRepository.findById(id);

        if(accountHasUsers.isEmpty()) throw new NonExistenceException(errorMessages.NON_EXISTENT_DATA);
        logger.info("ACCION DELETEBYID ACCOUNT_HAS_ROLE -> Encontre cuenta tiene usuario con exito");

        accountHasUserRepository.deleteById(id);
    }

}
