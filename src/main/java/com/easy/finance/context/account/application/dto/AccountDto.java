package com.easy.finance.context.account.application.dto;

import com.easy.finance.utils.constants.StateEnum;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class AccountDto {

    private Long id;
    private String name;
    private String description;
    private StateEnum state;

}
