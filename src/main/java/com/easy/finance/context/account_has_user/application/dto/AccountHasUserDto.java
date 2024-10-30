package com.easy.finance.context.account_has_user.application.dto;

import com.easy.finance.context.account.application.dto.AccountDto;
import com.easy.finance.context.account_has_user.domain.model.AccountHasUserId;
import com.easy.finance.context.user.application.dto.UserDto;
import com.easy.finance.utils.constants.StateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountHasUserDto {

    private AccountHasUserId id;
    private AccountDto account;
    private UserDto user;
    private StateEnum state;

    @Override
    public String toString() {
        return "AccountHasUserDto{" +
                "id=" + id.toString() +
                ", state='" + state + '\'' +
                '}';
    }
}
