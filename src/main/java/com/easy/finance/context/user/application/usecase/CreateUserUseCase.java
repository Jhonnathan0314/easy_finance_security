package com.easy.finance.context.user.application.usecase;

import com.easy.finance.context.user.domain.model.User;
import com.easy.finance.context.user.domain.port.UserRepository;
import com.easy.finance.utils.constants.ErrorMessages;
import com.easy.finance.utils.exceptions.DuplicatedException;
import com.easy.finance.utils.exceptions.InvalidBodyException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {

    private final Logger logger = Logger.getLogger(CreateUserUseCase.class.getName());

    private final UserRepository userRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();
    private final PasswordEncoder passwordEncoder;

    public User create(User user) throws InvalidBodyException, DuplicatedException {

        if(user.getPassword() == null) throw new InvalidBodyException(errorMessages.INVALID_BODY);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if(!user.isValid(user)) throw new InvalidBodyException(errorMessages.INVALID_BODY);

        logger.info("VALIDANDO DUPLICADOS");
        if(userRepository.findByEmail(user.getEmail()).isPresent()) throw new DuplicatedException(errorMessages.DUPLICATED);

        logger.info("PASE VALIDACION DUPLICADOS");
        return userRepository.create(user);
    }

}
