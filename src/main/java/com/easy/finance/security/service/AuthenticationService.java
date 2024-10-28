package com.easy.finance.security.service;

import com.easy.finance.context.user.application.usecase.CreateUserUseCase;
import com.easy.finance.context.user.application.usecase.FindByEmailUserUseCase;
import com.easy.finance.context.user.domain.model.User;
import com.easy.finance.security.jwt.JwtService;
import com.easy.finance.security.models.AuthResponse;
import com.easy.finance.security.models.LoginRequest;
import com.easy.finance.utils.constants.ErrorMessages;
import com.easy.finance.utils.exceptions.DuplicatedException;
import com.easy.finance.utils.exceptions.ForbiddenException;
import com.easy.finance.utils.exceptions.InvalidBodyException;
import com.easy.finance.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final ErrorMessages errorMessages = new ErrorMessages();
    private final FindByEmailUserUseCase findByEmailUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) throws NoResultsException, InvalidBodyException, ForbiddenException {

        if(!request.isValidRequest(request)) throw new InvalidBodyException(errorMessages.INVALID_BODY);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (Exception e) {
            throw new ForbiddenException(errorMessages.FORBIDDEN);
        }

        Optional<User> userDb = findByEmailUserUseCase.findByEmail(request.getEmail());
        if(userDb.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);

        Map<String, String> extraClaims = new HashMap<>();
        extraClaims.put("user_role", userDb.get().getRole().getName());

        String token = jwtService.generateToken(userDb.get(), extraClaims);

        logger.info("SECURITY - ACCION LOGIN -> Usuario logueado, token generado correctamente");
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(User request) throws InvalidBodyException, DuplicatedException {
        User user = User.builder()
                .email(request.getUsername())
                .password(request.getPassword())
                .name(request.getName())
                .lastName(request.getLastName())
                .build();

        user = createUserUseCase.create(user);

        Map<String, String> extraClaims = new HashMap<>();
        extraClaims.put("user_role", user.getRole().getName());

        String token = jwtService.generateToken(user, extraClaims);

        logger.info("SECURITY - ACCION REGISTER -> Usuario registrado con exito, token generado correctamente");
        return AuthResponse.builder()
                .token(token)
                .build();
    }

}