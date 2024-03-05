package com.example.rentalapp.constants;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ErrorTypeTest {

    @Test
    public void ErrorTypeTest(){
        assertNotEquals(ErrorType.valueOf("RENTALDAYS_INVALID"), is(notNullValue()));
        assertNotEquals(ErrorType.valueOf("DISCOUNTPERCENT_INVALID"), is(notNullValue()));
        assertNotEquals(ErrorType.valueOf("TOOL_NOT_FOUND"), is(notNullValue()));
    }
}
