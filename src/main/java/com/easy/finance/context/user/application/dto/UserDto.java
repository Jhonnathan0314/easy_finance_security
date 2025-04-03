package com.easy.finance.context.user.application.dto;

import com.easy.finance.context.role.application.dto.RoleDto;
import com.easy.finance.utils.constants.StateEnum;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String email;
    private String name;
    private String lastName;
    private String password;
    private RoleDto role;
    private StateEnum state;

}
