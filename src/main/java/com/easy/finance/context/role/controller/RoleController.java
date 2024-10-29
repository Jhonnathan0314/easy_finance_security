package com.easy.finance.context.role.controller;

import com.easy.finance.context.role.application.dto.RoleCreateDto;
import com.easy.finance.context.role.application.dto.RoleDto;
import com.easy.finance.context.role.application.dto.RoleUpdateDto;
import com.easy.finance.context.role.application.usecase.*;
import com.easy.finance.context.role.domain.model.Role;
import com.easy.finance.context.role.infrastructure.mappers.RoleCreateMapper;
import com.easy.finance.context.role.infrastructure.mappers.RoleMapper;
import com.easy.finance.context.role.infrastructure.mappers.RoleUpdateMapper;
import com.easy.finance.utils.exceptions.*;
import com.easy.finance.utils.http.HttpUtils;
import com.easy.finance.utils.messages.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/security/role")
@CrossOrigin("*")
@RequiredArgsConstructor
public class RoleController {

    private final FindAllRoleUseCase findAllRoleUseCase;
    private final FindByIdRoleUseCase findByIdRoleUseCase;
    private final CreateRoleUseCase createRoleUseCase;
    private final UpdateRoleUseCase updateRoleUseCase;
    private final DeleteByIdRoleUseCase deleteByIdRoleUseCase;

    private final RoleMapper roleMapper = new RoleMapper();
    private final RoleCreateMapper roleCreateMapper = new RoleCreateMapper();
    private final RoleUpdateMapper roleUpdateMapper = new RoleUpdateMapper();

    private final HttpUtils httpUtils = new HttpUtils();

    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleDto>>> findAll() {
        ApiResponse<List<RoleDto>> response = new ApiResponse<>();
        try {
            List<Role> roles = findAllRoleUseCase.findAll();
            response.setData(roleMapper.modelsToDtos(roles));
            return ResponseEntity.ok(response);
        }catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<RoleDto>> findById(@PathVariable Long id) {
        ApiResponse<RoleDto> response = new ApiResponse<>();
        try {
            Role role = findByIdRoleUseCase.findById(id);
            response.setData(roleMapper.modelToDto(role));
            return ResponseEntity.ok(response);
        }catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RoleDto>> create(@RequestBody RoleCreateDto dto) {
        ApiResponse<RoleDto> response = new ApiResponse<>();
        try {
            Role role = createRoleUseCase.create(roleCreateMapper.dtoToModel(dto));
            response.setData(roleMapper.modelToDto(role));
            return ResponseEntity.ok(response);
        }catch (DuplicatedException | InvalidBodyException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PutMapping
    public ResponseEntity<ApiResponse<RoleDto>> update(@RequestBody RoleUpdateDto dto) {
        ApiResponse<RoleDto> response = new ApiResponse<>();
        try {
            Role role = updateRoleUseCase.update(roleUpdateMapper.dtoToModel(dto));
            response.setData(roleMapper.modelToDto(role));
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
            deleteByIdRoleUseCase.deleteById(id);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }catch (NonExistenceException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

}
