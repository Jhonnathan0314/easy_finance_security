package com.easy.finance.context.role.application.usecase;

import com.easy.finance.context.role.domain.model.Role;
import com.easy.finance.context.role.domain.port.RoleRepository;
import com.easy.finance.utils.constants.ErrorMessages;
import com.easy.finance.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FindByIdRoleUseCase {

    private final Logger logger = Logger.getLogger(FindByIdRoleUseCase.class.getName());

    private final RoleRepository roleRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Role findById(Long id) throws NoResultsException {
        Optional<Role> roles = roleRepository.findById(id);
        
        if(roles.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        logger.info("ACCION FINDBYID ROLE -> Encontre rol con exito");

        return roles.get();
    }

}
