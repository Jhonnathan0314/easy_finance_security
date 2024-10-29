package com.easy.finance.context.account.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreateDto {

    private String name;
    private String description;

    @Override
    public String toString() {
        return "AccountDto{" +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
