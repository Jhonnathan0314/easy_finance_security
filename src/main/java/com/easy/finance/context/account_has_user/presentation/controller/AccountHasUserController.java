package com.easy.finance.context.account_has_user.presentation.controller;

import com.easy.finance.context.account_has_user.application.dto.AccountHasUserCreateDto;
import com.easy.finance.context.account_has_user.application.dto.AccountHasUserDto;
import com.easy.finance.context.account_has_user.application.usecase.*;
import com.easy.finance.context.account_has_user.domain.model.AccountHasUser;
import com.easy.finance.context.account_has_user.domain.model.AccountHasUserId;
import com.easy.finance.context.account_has_user.infrastructure.mappers.AccountHasUserCreateMapper;
import com.easy.finance.context.account_has_user.infrastructure.mappers.AccountHasUserMapper;
import com.easy.finance.utils.constants.StateEnum;
import com.easy.finance.utils.exceptions.DuplicatedException;
import com.easy.finance.utils.exceptions.InvalidBodyException;
import com.easy.finance.utils.exceptions.NoResultsException;
import com.easy.finance.utils.exceptions.NonExistenceException;
import com.easy.finance.utils.http.HttpUtils;
import com.easy.finance.utils.messages.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/security/account_has_user")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AccountHasUserController {

    private final FindByIdAccountAccountHasUserUseCase findByIdAccountAccountHasUserUseCase;
    private final FindByIdUserAccountHasUserUseCase findByIdUserAccountHasUserUseCase;
    private final FindByIdAccountHasUserUseCase findByIdAccountHasUserUseCase;
    private final FindByStateAccountHasUserUseCase findByStateAccountHasUserUseCase;
    private final CreateAccountHasUserUseCase createAccountHasUserUseCase;
    private final DeleteByIdAccountHasUserUseCase deleteByIdAccountHasUserUseCase;

    private final AccountHasUserMapper accountHasUserMapper = new AccountHasUserMapper();
    private final AccountHasUserCreateMapper accountHasUserCreateMapper = new AccountHasUserCreateMapper();

    private final HttpUtils httpUtils = new HttpUtils();

    @GetMapping("/account/{id}")
    public ResponseEntity<ApiResponse<List<AccountHasUserDto>>> findByIdAccount(@PathVariable Long id) {
        ApiResponse<List<AccountHasUserDto>> response = new ApiResponse<>();
        try {
            List<AccountHasUser> users = findByIdAccountAccountHasUserUseCase.findByIdAccount(id);
            response.setData(accountHasUserMapper.modelsToDtos(users));
            return ResponseEntity.ok(response);
        }catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse<List<AccountHasUserDto>>> findByIdUser(@PathVariable Long id) {
        ApiResponse<List<AccountHasUserDto>> response = new ApiResponse<>();
        try {
            List<AccountHasUser> users = findByIdUserAccountHasUserUseCase.findByIdUser(id);
            response.setData(accountHasUserMapper.modelsToDtos(users));
            return ResponseEntity.ok(response);
        }catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @GetMapping("/account/{idAccount}/user/{idUser}")
    public ResponseEntity<ApiResponse<AccountHasUserDto>> findById(@PathVariable Long idAccount, @PathVariable Long idUser) {
        ApiResponse<AccountHasUserDto> response = new ApiResponse<>();
        try {
            AccountHasUserId id = AccountHasUserId.builder()
                    .idAccount(idAccount)
                    .idUser(idUser)
                    .build();
            AccountHasUser user = findByIdAccountHasUserUseCase.findById(id);
            response.setData(accountHasUserMapper.modelToDto(user));
            return ResponseEntity.ok(response);
        }catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<ApiResponse<List<AccountHasUserDto>>> findByState(@PathVariable StateEnum state) {
        ApiResponse<List<AccountHasUserDto>> response = new ApiResponse<>();
        try {
            List<AccountHasUser> model = findByStateAccountHasUserUseCase.findByState(state);
            response.setData(accountHasUserMapper.modelsToDtos(model));
            return ResponseEntity.ok(response);
        }catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AccountHasUserDto>> create(@RequestBody AccountHasUserCreateDto dto) {
        ApiResponse<AccountHasUserDto> response = new ApiResponse<>();
        try {
            AccountHasUser model = createAccountHasUserUseCase.create(accountHasUserCreateMapper.dtoToModel(dto));
            response.setData(accountHasUserMapper.modelToDto(model));
            return ResponseEntity.ok(response);
        }catch (NonExistenceException | DuplicatedException | InvalidBodyException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @DeleteMapping("/account/{idAccount}/user/{idUser}")
    public ResponseEntity<ApiResponse<Object>> deleteById(@PathVariable Long idAccount, @PathVariable Long idUser) {
        ApiResponse<Object> response = new ApiResponse<>();
        try {
            AccountHasUserId id = AccountHasUserId.builder()
                    .idAccount(idAccount)
                    .idUser(idUser)
                    .build();
            deleteByIdAccountHasUserUseCase.deleteById(id);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }catch (NonExistenceException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

}
