package com.example.rentalapp.constants;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ToolEntryTest {
    @Test
    public void ErrorTypeTest(){
        for (String s : Arrays.asList("LADW","CHNS", "JAKD", "JAKR")) {
            assertNotEquals(ToolEntry.valueOf(s), is(notNullValue()));
        }
    }
}
