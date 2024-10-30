package com.easy.finance.context.account_has_user.application.usecase;

import com.easy.finance.context.account_has_user.domain.model.AccountHasUser;
import com.easy.finance.context.account_has_user.domain.port.AccountHasUserRepository;
import com.easy.finance.utils.constants.ErrorMessages;
import com.easy.finance.utils.constants.StateEnum;
import com.easy.finance.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FindByStateAccountHasUserUseCase {

    private final Logger logger = Logger.getLogger(FindByStateAccountHasUserUseCase.class.getName());

    private final AccountHasUserRepository accountHasUserRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public List<AccountHasUser> findByState(StateEnum state) throws NoResultsException {
        List<AccountHasUser> accountHasUsers = accountHasUserRepository.findByState(state);

        if(accountHasUsers.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        logger.info("ACCION FINDBYSTATE ACCOUNT_HAS_ROLE -> Encontre cuenta tiene usuario con exito");

        return accountHasUsers;
    }

}
