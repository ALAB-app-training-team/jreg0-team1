package com.example.jreg0.error;

import lombok.*;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
}
