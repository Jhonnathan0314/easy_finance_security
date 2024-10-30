package com.easy.finance.context.account_has_user.infrastructure.persistence;

import com.easy.finance.context.account.infrastructure.persistence.AccountEntity;
import com.easy.finance.context.account_has_user.domain.model.AccountHasUserId;
import com.easy.finance.context.role.infrastructure.persistence.RoleEntity;
import com.easy.finance.context.user.infrastructure.persistence.UserEntity;
import com.easy.finance.utils.constants.StateEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account_has_user")
public class AccountHasUserEntity {

    @EmbeddedId
    private AccountHasUserId id;

    @ManyToOne
    @MapsId("idAccount")
    @JoinColumn(name = "id_account", updatable = false)
    private AccountEntity idAccount;

    @ManyToOne
    @MapsId("idUser")
    @JoinColumn(name = "id_user", updatable = false)
    private UserEntity idUser;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private Timestamp creationDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private Timestamp updateDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private StateEnum state;

    @PrePersist
    protected void onCreate() {
        this.state = StateEnum.active;
    }

}
