package com.example.rentalapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Global exception handler for handling BusinessException across all controllers.
 */
@ControllerAdvice
public class BusinessExceptionHandler {

    /**
     * Exception handler method for BusinessException.
     * @param be The BusinessException object.
     * @param webRequest The WebRequest object.
     * @return ResponseEntity containing error details and HTTP status code.
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Errors> handleBusinessException(BusinessException be, WebRequest webRequest){
        Errors errors = new Errors();
        if(be instanceof NotFoundException){
            errors.setErrors(be.getErrors());
            return new ResponseEntity<Errors>(errors, HttpStatus.NOT_FOUND);
        }
        errors.setErrors(be.getErrors());
        return new ResponseEntity<Errors>(errors, HttpStatus.BAD_REQUEST);
    }
}
