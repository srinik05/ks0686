package com.example.rentalapp.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Errors class represents a collection of Error objects.
 */
@Getter
@Setter
public class Errors {
    private List<Error> errors;
}
