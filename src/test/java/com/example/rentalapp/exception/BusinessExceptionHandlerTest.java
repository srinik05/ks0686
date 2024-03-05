package com.example.rentalapp.exception;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class BusinessExceptionHandlerTest {

    private BusinessExceptionHandler businessExceptionHandler;
    @Mock
    WebRequest webRequest;

    @BeforeEach
    public void setuup(){
        businessExceptionHandler = new BusinessExceptionHandler();
    }

    @Test
    public void test_handleBusinessException(){
        List<Error> errors = new ArrayList<>();
        Error error= new Error("code" , "Rental day count must be 1 or greater.", 400, UUID.randomUUID().toString());
        errors.add(error);
        BusinessException be  = new BusinessException(errors);
        ResponseEntity<Errors> entity = businessExceptionHandler.handleBusinessException(be, webRequest);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), entity.getBody().getErrors().get(0).getStatus());
    }
    @Test
    public void test_NotFoundException(){
        List<Error> errors = new ArrayList<>();
        Error error= new Error("code" , "Invalid tool code. Tool not found.", 404, UUID.randomUUID().toString());
        errors.add(error);
        BusinessException be  = new BusinessException(errors);
        ResponseEntity<Errors> entity = businessExceptionHandler.handleBusinessException(be, webRequest);
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), entity.getBody().getErrors().get(0).getStatus());
    }
}
