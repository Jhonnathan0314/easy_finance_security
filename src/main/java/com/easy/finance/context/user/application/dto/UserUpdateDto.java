package com.easy.finance.context.user.application.dto;

import com.easy.finance.context.role.application.dto.RoleUpdateDto;
import com.easy.finance.utils.constants.StateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {

    private Long id;
    private String email;
    private String name;
    private String lastName;
    private String password;
    private RoleUpdateDto role;
    private StateEnum state;
}
