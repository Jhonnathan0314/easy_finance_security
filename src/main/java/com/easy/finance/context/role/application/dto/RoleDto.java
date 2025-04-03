package com.easy.finance.context.role.application.dto;

import com.easy.finance.utils.constants.StateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RoleDto {

    private Long id;
    private String name;
    private StateEnum state;

}
