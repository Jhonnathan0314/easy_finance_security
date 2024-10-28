package com.easy.finance.context.user.application.dto;

import com.easy.finance.context.role.application.dto.RoleDto;
import com.easy.finance.utils.constants.StateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String email;
    private String name;
    private String lastName;
    private String password;
    private RoleDto role;
    private StateEnum state;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto user = (UserDto) o;
        return Objects.equals(
                getId(), user.getId()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getName(), user.getName()) &&
                Objects.equals(getLastName(), user.getLastName());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='******'" +
                ", role=" + role.toString() +
                ", state='" + state.toString() + '\'' +
                '}';
    }

}
