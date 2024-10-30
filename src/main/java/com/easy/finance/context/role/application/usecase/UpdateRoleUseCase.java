package com.easy.finance.context.role.application.usecase;

import com.easy.finance.context.role.domain.model.Role;
import com.easy.finance.context.role.domain.port.RoleRepository;
import com.easy.finance.utils.constants.ErrorMessages;
import com.easy.finance.utils.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class UpdateRoleUseCase {

    private final Logger logger = Logger.getLogger(UpdateRoleUseCase.class.getName());

    private final RoleRepository roleRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Role update(Role role) throws DuplicatedException, InvalidBodyException, NoChangesException, NoIdReceivedException, NonExistenceException {

        if(role.getId() == null) throw new NoIdReceivedException(errorMessages.NO_ID_RECEIVED);
        logger.info("ACCION UPDATE ROLE -> Id validado con exito");

        if(!role.isValid(role)) throw new InvalidBodyException(errorMessages.INVALID_BODY);
        logger.info("ACCION UPDATE ROLE -> Body validado con exito");

        Optional<Role> roleIdOpt = roleRepository.findById(role.getId());

        if(roleIdOpt.isEmpty()) throw new NonExistenceException(errorMessages.NON_EXISTENT_DATA);
        logger.info("ACCION UPDATE ROLE -> Validacion rol existente con exito");

        if(roleIdOpt.get().getName().equals(role.getName())) throw new NoChangesException(errorMessages.NO_CHANGES);
        logger.info("ACCION UPDATE ROLE -> Validacion cambios a aplicar con exito");

        Optional<Role> roleNameOpt = roleRepository.findByName(role.getName());

        if(roleNameOpt.isPresent()) throw new DuplicatedException(errorMessages.DUPLICATED);
        logger.info("ACCION UPDATE ROLE -> Validacion no duplicado exitosa");

        return roleRepository.update(role);
    }

}
