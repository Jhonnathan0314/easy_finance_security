package com.easy.finance.context.user.application.usecase;

import com.easy.finance.context.user.domain.model.User;
import com.easy.finance.context.user.domain.port.UserRepository;
import com.easy.finance.utils.constants.ErrorMessages;
import com.easy.finance.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FindByIdUserUseCase {

    private final Logger logger = Logger.getLogger(FindByIdUserUseCase.class.getName());

    private final UserRepository userRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public User findById(Long id) throws NoResultsException {
        Optional<User> optUser = userRepository.findById(id);

        if(optUser.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);

        return optUser.get();
    }
}
