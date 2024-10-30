package com.easy.finance.context.user.presentation.controller;

import com.easy.finance.context.user.application.dto.UserCreateDto;
import com.easy.finance.context.user.application.dto.UserDto;
import com.easy.finance.context.user.application.dto.UserResponseDto;
import com.easy.finance.context.user.application.dto.UserUpdateDto;
import com.easy.finance.context.user.application.usecase.*;
import com.easy.finance.context.user.domain.model.User;
import com.easy.finance.context.user.infrastructure.mappers.UserMapper;
import com.easy.finance.context.user.infrastructure.mappers.UserCreateMapper;
import com.easy.finance.context.user.infrastructure.mappers.UserUpdateMapper;
import com.easy.finance.utils.exceptions.*;
import com.easy.finance.utils.http.HttpUtils;
import com.easy.finance.utils.messages.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/security/user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

    private final Logger logger = Logger.getLogger(UserController.class.getName());

    private final FindAllUserUseCase findAllUserUseCase;
    private final FindByIdUserUseCase findByIdUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteByIdUserUseCase deleteByIdUserUseCase;

    private final UserMapper userMapper = new UserMapper();
    private final UserCreateMapper userCreateMapper = new UserCreateMapper();
    private final UserUpdateMapper userUpdateMapper = new UserUpdateMapper();

    private final HttpUtils httpUtils = new HttpUtils();

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> findAll() {
        logger.info("ACCION FINDALL USER -> Inicia consulta usuarios");
        ApiResponse<List<UserResponseDto>> response = new ApiResponse<>();
        try {
            List<User> users = findAllUserUseCase.findAll();
            List<UserDto> usersDto = userMapper.modelsToDtos(users);
            List<UserResponseDto> userResponseDtos = new ArrayList<>();
            UserResponseDto responseDto = new UserResponseDto();
            usersDto.forEach(userDto -> userResponseDtos.add(responseDto.dtoToResponseDto(userDto)));
            response.setData(userResponseDtos);
            return ResponseEntity.ok(response);
        }catch (NoResultsException e) {
            logger.info("ACCION FINDALL USER -> No encontre usuarios - NoResultsException");
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<UserResponseDto>> findById(@PathVariable Long id) {
        logger.info("ACCION FINDBYID USER -> Inicia consulta usuario con id: " + id);
        ApiResponse<UserResponseDto> response = new ApiResponse<>();
        try {
            User user = findByIdUserUseCase.findById(id);
            UserDto userDto = userMapper.modelToDto(user);
            UserResponseDto responseDto = new UserResponseDto();
            response.setData(responseDto.dtoToResponseDto(userDto));
            return ResponseEntity.ok(response);
        }catch (NoResultsException e) {
            logger.info("ACCION FINDBYID USER -> No encontre usuario indicado - NoResultsException");
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDto>> create(@RequestBody UserCreateDto dto) {
        logger.info("ACCION CREATE USER -> Inicia creacion usuario con body: " + dto.toString());
        ApiResponse<UserResponseDto> response = new ApiResponse<>();
        try {
            User user = userCreateMapper.dtoToModel(dto);
            UserDto userResponse = userMapper.modelToDto(createUserUseCase.create(user));
            UserResponseDto responseDto = new UserResponseDto();
            response.setData(responseDto.dtoToResponseDto(userResponse));
            return ResponseEntity.ok(response);
        }catch (DuplicatedException | InvalidBodyException e) {
            logger.info("ACCION CREATE USER -> Ha ocurrido un error al crear usuario: " + e.getMessage());
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PutMapping
    public ResponseEntity<ApiResponse<UserResponseDto>> update(@RequestBody UserUpdateDto dto) {
        logger.info("ACCION UPDATE USER -> Inicia actualizacion usuario con body: " + dto.toString());
        ApiResponse<UserResponseDto> response = new ApiResponse<>();
        try {
            User user = userUpdateMapper.dtoToModel(dto);
            UserDto userResponse = userMapper.modelToDto(updateUserUseCase.update(user));
            UserResponseDto responseDto = new UserResponseDto();
            response.setData(responseDto.dtoToResponseDto(userResponse));
            return ResponseEntity.ok(response);
        }catch (NoIdReceivedException | InvalidBodyException | NonExistenceException | NoChangesException | DuplicatedException e) {
            logger.info("ACCION UPDATE USER -> Ha ocurrido un error al actualizar usuario: " + e.getMessage());
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Object>> deleteById(@PathVariable Long id) {
        logger.info("ACCION DELETEBYID USER -> Inicia eliminacion usuario con id: " + id);
        ApiResponse<Object> response = new ApiResponse<>();
        try {
            deleteByIdUserUseCase.deleteById(id);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }catch (NonExistenceException e) {
            logger.info("ACCION DELETEBYID USER -> Ha ocurrido un error al eliminar usuario: " + e.getMessage());
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

}
