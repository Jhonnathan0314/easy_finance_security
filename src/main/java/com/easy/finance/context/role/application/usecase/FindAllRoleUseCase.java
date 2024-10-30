package com.easy.finance.context.role.application.usecase;

import com.easy.finance.context.role.domain.model.Role;
import com.easy.finance.context.role.domain.port.RoleRepository;
import com.easy.finance.utils.constants.ErrorMessages;
import com.easy.finance.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FindAllRoleUseCase {

    private final Logger logger = Logger.getLogger(FindAllRoleUseCase.class.getName());

    private final RoleRepository roleRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public List<Role> findAll() throws NoResultsException {
        List<Role> roles = roleRepository.findAll();

        if(roles.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        logger.info("ACCION FINDALL ROLE -> Encontre roles con exito");

        return roles;
    }

}
