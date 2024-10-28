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

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", state='" + state.toString() + '\'' +
                '}';
    }
}
