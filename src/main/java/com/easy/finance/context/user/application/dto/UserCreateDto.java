package com.easy.finance.context.user.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {

    private String email;
    private String name;
    private String lastName;
    private String password;

}
