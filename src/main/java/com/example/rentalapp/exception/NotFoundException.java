package com.example.rentalapp.exception;

import java.util.List;

/**
 * NotFoundException class represents an exception for resource not found errors.
 * It extends the BusinessException class.
 */
public class NotFoundException extends BusinessException {

    /**
     * Constructs a new NotFoundException with a list of errors.
     *
     * @param errors List of Error objects representing the errors.
     */
    public NotFoundException(List<Error> errors) {
        super(errors);
    }
}
