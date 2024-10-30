package com.easy.finance.context.account_has_user.application.usecase;

import com.easy.finance.context.account_has_user.domain.model.AccountHasUser;
import com.easy.finance.context.account_has_user.domain.port.AccountHasUserRepository;
import com.easy.finance.utils.constants.ErrorMessages;
import com.easy.finance.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FindByIdAccountAccountHasUserUseCase {

    private final Logger logger = Logger.getLogger(FindByIdAccountAccountHasUserUseCase.class.getName());

    private final AccountHasUserRepository accountHasUserRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public List<AccountHasUser> findByIdAccount(Long idAccount) throws NoResultsException {
        List<AccountHasUser> accountHasUsers = accountHasUserRepository.findByIdAccount(idAccount);

        if(accountHasUsers.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        logger.info("ACCION FINDBYIDACCOUNT ACCOUNT_HAS_ROLE -> Encontre cuenta tiene usuario con exito");

        return accountHasUsers;
    }

}
