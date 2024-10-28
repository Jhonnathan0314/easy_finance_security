package com.easy.finance.context.role.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleCreateDto {

    private String name;

    @Override
    public String toString() {
        return "RoleDto{" +
                "name='" + name + '\'' +
                '}';
    }
}
