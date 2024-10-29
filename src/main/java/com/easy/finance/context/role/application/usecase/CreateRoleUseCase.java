package com.easy.finance.context.role.application.usecase;

import com.easy.finance.context.role.domain.model.Role;
import com.easy.finance.context.role.domain.port.RoleRepository;
import com.easy.finance.utils.constants.ErrorMessages;
import com.easy.finance.utils.exceptions.DuplicatedException;
import com.easy.finance.utils.exceptions.InvalidBodyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateRoleUseCase {

    private final RoleRepository roleRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Role create(Role role) throws DuplicatedException, InvalidBodyException {

        if(!role.isValid(role)) throw new InvalidBodyException(errorMessages.INVALID_BODY);

        Optional<Role> roleOpt = roleRepository.findByName(role.getName());

        if(roleOpt.isPresent()) throw new DuplicatedException(errorMessages.DUPLICATED);

        return roleRepository.create(role);
    }

}
