package com.easy.finance.context.account_has_user.domain.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AccountHasUserId implements Serializable {

    private Long idUser;
    private Long idAccount;

    public boolean isValid(AccountHasUserId accountHasUserId) {
        return accountHasUserId.getIdUser() != null && accountHasUserId.getIdAccount() != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountHasUserId that)) return false;
        return Objects.equals(getIdUser(), that.getIdUser()) && Objects.equals(getIdAccount(), that.getIdAccount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdUser(), getIdAccount());
    }

    @Override
    public String toString() {
        return "AccountHasUserId{" +
                "idUser=" + idUser +
                ", idAccount=" + idAccount +
                '}';
    }
}
