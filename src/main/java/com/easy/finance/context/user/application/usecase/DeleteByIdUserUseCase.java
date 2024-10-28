package com.easy.finance.context.user.application.usecase;

import com.easy.finance.context.user.domain.model.User;
import com.easy.finance.context.user.domain.port.UserRepository;
import com.easy.finance.utils.constants.ErrorMessages;
import com.easy.finance.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class DeleteByIdUserUseCase {

    private final Logger logger = Logger.getLogger(DeleteByIdUserUseCase.class.getName());

    private final UserRepository userRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public void deleteById(Long id) throws NonExistenceException {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) throw new NonExistenceException(errorMessages.NON_EXISTENT_DATA);
        userRepository.deleteById(id);
    }
}
