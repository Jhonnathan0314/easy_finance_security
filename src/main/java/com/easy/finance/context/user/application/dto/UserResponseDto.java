package com.easy.finance.context.user.application.dto;

import com.easy.finance.context.role.application.dto.RoleDto;
import com.easy.finance.utils.constants.StateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private Long id;
    private String email;
    private String name;
    private String lastName;
    private RoleDto role;
    private StateEnum state;

    public UserResponseDto dtoToResponseDto(UserDto dto) {
        return UserResponseDto.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .name(dto.getName())
                .lastName(dto.getLastName())
                .role(dto.getRole())
                .state(dto.getState())
                .build();
    }

}
