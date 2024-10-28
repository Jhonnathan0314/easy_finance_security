package com.easy.finance.context.user.presentation.controller;

import com.easy.finance.context.user.application.dto.UserCreateDto;
import com.easy.finance.context.user.application.dto.UserDto;
import com.easy.finance.context.user.application.usecase.CreateUserUseCase;
import com.easy.finance.context.user.domain.model.User;
import com.easy.finance.context.user.infrastructure.mappers.UserMapper;
import com.easy.finance.context.user.infrastructure.mappers.UserCreateMapper;
import com.easy.finance.utils.exceptions.DuplicatedException;
import com.easy.finance.utils.exceptions.InvalidBodyException;
import com.easy.finance.utils.http.HttpUtils;
import com.easy.finance.utils.messages.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/security/user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

    private final CreateUserUseCase createUserUseCase;

    private final UserMapper userMapper = new UserMapper();
    private final UserCreateMapper userCreateMapper = new UserCreateMapper();

    private final HttpUtils httpUtils = new HttpUtils();

    @PostMapping
    public ResponseEntity<ApiResponse<UserDto>> create(@RequestBody UserCreateDto dto) {
        ApiResponse<UserDto> response = new ApiResponse<>();
        try {
            User user = userCreateMapper.dtoToModel(dto);
            UserDto userResponse = userMapper.modelToDto(createUserUseCase.create(user));
            response.setData(userResponse);
            return ResponseEntity.ok(response);
        }catch (DuplicatedException | InvalidBodyException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

}
