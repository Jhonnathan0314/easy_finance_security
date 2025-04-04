package com.easy.finance.context.role.application.usecase;

import com.easy.finance.context.role.domain.model.Role;
import com.easy.finance.context.role.domain.port.RoleRepository;
import com.easy.finance.utils.constants.ErrorMessages;
import com.easy.finance.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class DeleteByIdRoleUseCase {

    private final Logger logger = Logger.getLogger(DeleteByIdRoleUseCase.class.getName());

    private final RoleRepository roleRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public void deleteById(Long id) throws NonExistenceException {
        Optional<Role> roles = roleRepository.findById(id);
        
        if(roles.isEmpty()) throw new NonExistenceException(errorMessages.NON_EXISTENT_DATA);
        logger.info("ACCION DELETEBYID ROLE -> Encontre rol con exito");

        roleRepository.deleteById(id);
    }

}
