package com.easy.finance.context.user.application.usecase;

import com.easy.finance.context.user.domain.model.User;
import com.easy.finance.context.user.domain.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindByEmailUserUseCase {

    private final UserRepository userRepository;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
