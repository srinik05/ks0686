package com.example.rentalapp.constants;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ToolTypeTest {
    @Test
    public void ErrorTypeTest(){
        for (String s : Arrays.asList("CHAINSAW", "JACKHAMMER", "LADDER")) {
            assertNotEquals(ToolType.valueOf(s), is(notNullValue()));
        }
    }
}
