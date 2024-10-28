package com.easy.finance.context.user.domain.model;

import com.easy.finance.context.role.domain.model.Role;
import com.easy.finance.utils.constants.StateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    private Long id;
    private String email;
    private String name;
    private String lastName;
    private String password;
    private Role role;
    private StateEnum state;

    public boolean isValid(User user) {
        if(user.getEmail() == null ||
                user.getName() == null ||
                user.getLastName() == null) return false;

        return !user.getEmail().isEmpty() &&
                !user.getName().isEmpty() &&
                !user.getLastName().isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(
                getId(), user.getId()) &&
                Objects.equals(getUsername(), user.getUsername()) &&
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
                ", state='" + state + '\'' +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public String getUsername() {
        return email;
    }
}
