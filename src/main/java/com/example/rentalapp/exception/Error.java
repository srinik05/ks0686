package com.example.rentalapp.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Error class represents details of an error occurred in the application.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error {
    private String code;
    private String message;
    private Integer status;
    private String traceId = UUID.randomUUID().toString();
}
