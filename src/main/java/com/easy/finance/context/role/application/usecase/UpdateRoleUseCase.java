package com.easy.finance.context.role.application.usecase;

import com.easy.finance.context.role.domain.model.Role;
import com.easy.finance.context.role.domain.port.RoleRepository;
import com.easy.finance.utils.constants.ErrorMessages;
import com.easy.finance.utils.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateRoleUseCase {

    private final RoleRepository roleRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Role update(Role role) throws DuplicatedException, InvalidBodyException, NoChangesException, NoIdReceivedException, NonExistenceException {

        if(role.getId() == null) throw new NoIdReceivedException(errorMessages.NO_ID_RECEIVED);

        if(!role.isValid(role)) throw new InvalidBodyException(errorMessages.INVALID_BODY);

        Optional<Role> roleIdOpt = roleRepository.findById(role.getId());

        if(roleIdOpt.isEmpty()) throw new NonExistenceException(errorMessages.NON_EXISTENT_DATA);

        if(roleIdOpt.get().getName().equals(role.getName())) throw new NoChangesException(errorMessages.NO_CHANGES);

        Optional<Role> roleNameOpt = roleRepository.findByName(role.getName());

        if(roleNameOpt.isPresent()) throw new DuplicatedException(errorMessages.DUPLICATED);

        return roleRepository.update(role);
    }

}
