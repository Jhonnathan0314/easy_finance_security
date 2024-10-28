package com.easy.finance.security.controllers;

import com.easy.finance.context.user.application.dto.UserCreateDto;
import com.easy.finance.context.user.infrastructure.mappers.UserCreateMapper;
import com.easy.finance.security.models.AuthResponse;
import com.easy.finance.security.models.LoginRequest;
import com.easy.finance.security.service.AuthenticationService;
import com.easy.finance.utils.exceptions.DuplicatedException;
import com.easy.finance.utils.exceptions.ForbiddenException;
import com.easy.finance.utils.exceptions.InvalidBodyException;
import com.easy.finance.utils.exceptions.NoResultsException;
import com.easy.finance.utils.http.HttpUtils;
import com.easy.finance.utils.messages.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/security/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService authService;
    private final HttpUtils httpUtils = new HttpUtils();
    private final UserCreateMapper userCreateMapper = new UserCreateMapper();

    @PostMapping(value = "login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {
        logger.info("ACCION LOGIN -> Inicia proceso de login con request: " + request.toString());
        ApiResponse<AuthResponse> response = new ApiResponse<>();
        try {
            response.setData(authService.login(request));
            logger.info("ACCION LOGIN -> Finaliza proceso de login satisfactorio");
            return ResponseEntity.ok(response);
        } catch (NoResultsException | InvalidBodyException | ForbiddenException e) {
            logger.error("ACCION LOGIN -> Finaliza proceso de login con error: " + e.getMessage());
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PostMapping(value = "register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody UserCreateDto request) {
        logger.info("ACCION REGISTER -> Inicia proceso de register con request: " + request.toString());
        ApiResponse<AuthResponse> response = new ApiResponse<>();
        try {
            response.setData(authService.register(userCreateMapper.dtoToModel(request)));
            logger.info("ACCION REGISTER -> Finaliza proceso de register satisfactorio");
            return ResponseEntity.ok(response);
        } catch (InvalidBodyException | DuplicatedException e) {
            logger.error("ACCION REGISTER -> Finaliza proceso de register con error: " + e.getMessage());
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

}
