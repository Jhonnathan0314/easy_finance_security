package com.easy.finance.context.role.application.dto;

import com.easy.finance.utils.constants.StateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleUpdateDto {

    private Long id;
    private String name;
    private StateEnum state;

    @Override
    public String toString() {
        return "RoleDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", state='" + state.toString() + '\'' +
                '}';
    }
}
