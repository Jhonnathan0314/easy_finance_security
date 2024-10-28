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

@RestController
@RequestMapping("/api/v1/security/user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

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
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<UserResponseDto>> findById(@PathVariable Long id) {
        ApiResponse<UserResponseDto> response = new ApiResponse<>();
        try {
            User user = findByIdUserUseCase.findById(id);
            UserDto userDto = userMapper.modelToDto(user);
            UserResponseDto responseDto = new UserResponseDto();
            response.setData(responseDto.dtoToResponseDto(userDto));
            return ResponseEntity.ok(response);
        }catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDto>> create(@RequestBody UserCreateDto dto) {
        ApiResponse<UserResponseDto> response = new ApiResponse<>();
        try {
            User user = userCreateMapper.dtoToModel(dto);
            UserDto userResponse = userMapper.modelToDto(createUserUseCase.create(user));
            UserResponseDto responseDto = new UserResponseDto();
            response.setData(responseDto.dtoToResponseDto(userResponse));
            return ResponseEntity.ok(response);
        }catch (DuplicatedException | InvalidBodyException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PutMapping
    public ResponseEntity<ApiResponse<UserResponseDto>> update(@RequestBody UserUpdateDto dto) {
        ApiResponse<UserResponseDto> response = new ApiResponse<>();
        try {
            User user = userUpdateMapper.dtoToModel(dto);
            UserDto userResponse = userMapper.modelToDto(updateUserUseCase.update(user));
            UserResponseDto responseDto = new UserResponseDto();
            response.setData(responseDto.dtoToResponseDto(userResponse));
            return ResponseEntity.ok(response);
        }catch (NoIdReceivedException | InvalidBodyException | NonExistenceException | NoChangesException | DuplicatedException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Object>> deleteById(@PathVariable Long id) {
        ApiResponse<Object> response = new ApiResponse<>();
        try {
            deleteByIdUserUseCase.deleteById(id);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }catch (NonExistenceException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

}
