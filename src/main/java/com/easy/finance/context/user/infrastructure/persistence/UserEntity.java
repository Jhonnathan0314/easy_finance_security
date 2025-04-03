package com.easy.finance.context.user.infrastructure.persistence;

import com.easy.finance.context.account_has_user.infrastructure.persistence.AccountHasUserEntity;
import com.easy.finance.context.role.infrastructure.persistence.RoleEntity;
import com.easy.finance.utils.constants.StateEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "id_role", updatable = false)
    private RoleEntity role;

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
        this.role = RoleEntity.builder()
                .id(1L)
                .name("admin")
                .build();
    }

}
