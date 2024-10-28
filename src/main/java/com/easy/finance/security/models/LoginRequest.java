package com.easy.finance.security.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    private String email;
    private String password;

    public boolean isValidRequest(LoginRequest request) {
        if (request.getEmail() == null || request.getPassword() == null) return false;
        return !request.getEmail().isEmpty() && !request.getPassword().isEmpty();
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "email='" + email + '\'' +
                ", password='******'" +
                '}';
    }
}
