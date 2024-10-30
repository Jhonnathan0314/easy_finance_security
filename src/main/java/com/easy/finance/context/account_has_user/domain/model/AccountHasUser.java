package com.easy.finance.context.account_has_user.domain.model;

import com.easy.finance.context.account.domain.model.Account;
import com.easy.finance.context.user.domain.model.User;
import com.easy.finance.utils.constants.StateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountHasUser {

    private AccountHasUserId id;
    private Account idAccount;
    private User idUser;
    private StateEnum state;

    public boolean isValid(AccountHasUser accountHasUser) {
        return accountHasUser.getId().isValid(accountHasUser.getId());
    }

    @Override
    public String toString() {
        return "AccountHasUser{" +
                "id=" + id +
                ", idAccount=" + idAccount +
                ", idUser=" + idUser +
                ", state=" + state +
                '}';
    }
}
