package com.easy.finance.context.user.application.usecase;

import com.easy.finance.context.user.domain.model.User;
import com.easy.finance.context.user.domain.port.UserRepository;
import com.easy.finance.utils.constants.ErrorMessages;
import com.easy.finance.utils.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class UpdateUserUseCase {

    private final Logger logger = Logger.getLogger(UpdateUserUseCase.class.getName());

    private final UserRepository userRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();
    private final PasswordEncoder passwordEncoder;

    public User update(User user) throws NoIdReceivedException, InvalidBodyException, NonExistenceException, NoChangesException, DuplicatedException {

        if(user.getId() == null) throw new NoIdReceivedException(errorMessages.NO_ID_RECEIVED);
        logger.info("ACCION UPDATE USER -> Id validado con exito");

        if(!user.isValid(user)) throw new InvalidBodyException(errorMessages.INVALID_BODY);
        logger.info("ACCION UPDATE USER -> Body validado con exito");

        Optional<User> optUser = userRepository.findById(user.getId());
        if(optUser.isEmpty()) throw new NonExistenceException(errorMessages.NON_EXISTENT_DATA);
        logger.info("ACCION UPDATE USER -> Validacion usuario existente con exito");

        User userDb = optUser.get();

        user.setRole(userDb.getRole());
        user.setState(userDb.getState());

        if(userDb.equals(user) && (user.getPassword() == null || user.getPassword().isEmpty())) throw new NoChangesException(errorMessages.NO_CHANGES);
        logger.info("ACCION UPDATE USER -> Validacion cambios a aplicar con exito");

        if(!user.getEmail().equals(userDb.getEmail())){
            if(userRepository.findByEmail(user.getEmail()).isPresent()) throw new DuplicatedException(errorMessages.DUPLICATED);
            logger.info("ACCION UPDATE USER -> Validacion no duplicado exitosa");
        }

        if(user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(userDb.getPassword());
        }

        return userRepository.update(user);
    }
}
