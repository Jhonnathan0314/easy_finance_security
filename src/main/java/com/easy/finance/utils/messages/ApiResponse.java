package com.easy.finance.utils.messages;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiResponse<T> {
    private T data;
    private ErrorMessage error;
}
