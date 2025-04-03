package com.easy.finance.context.role.domain.model;

import com.easy.finance.utils.constants.StateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    private Long id;
    private String name;
    private StateEnum state;

    public boolean isValid(Role role) {
        if(role.getName() == null) return false;

        return !role.getName().isEmpty();
    }

}
