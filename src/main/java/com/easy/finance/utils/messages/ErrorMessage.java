package com.easy.finance.utils.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErrorMessage {
    private int code;
    private String title;
    private String detail;
}
