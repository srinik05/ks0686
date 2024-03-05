package com.example.rentalapp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Custom exception class for handling business exceptions.
 */
@Getter
@Setter
@AllArgsConstructor
public class BusinessException extends Exception {
    private static  final Long serialVersionUID =1L;
    private transient List<Error> errors;
}
