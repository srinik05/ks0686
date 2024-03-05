package com.example.rentalapp.exception;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

public class ErrorTest {

    @Test
    public void testErrorModels(){
        BeanTester beanTester = new BeanTester();
        beanTester.testBean(Error.class);
        beanTester.testBean(Errors.class);
    }
}
