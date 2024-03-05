package com.example.rentalapp.validator;

import com.example.rentalapp.constants.Constants;
import com.example.rentalapp.constants.ErrorType;
import com.example.rentalapp.exception.BusinessException;
import com.example.rentalapp.exception.Error;
import com.example.rentalapp.model.CheckoutRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ToolValidatorTest {

    @InjectMocks
    private ToolValidator toolValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateRequest_ValidRequest_NoException() {
        CheckoutRequest request = new CheckoutRequest();
        request.setRentalDays(3);
        request.setDiscountPercent(20);
        request.setCheckoutDate(LocalDate.now());
        request.setToolCode("LADW");
        assertDoesNotThrow(() -> toolValidator.validateRequest(request, "12345"));
    }

    @Test
    void validateRequest_InvalidRentalDays_ExceptionThrown() {
        CheckoutRequest request = new CheckoutRequest();
        request.setRentalDays(0);
        request.setDiscountPercent(20);

        BusinessException exception = assertThrows(BusinessException.class, () -> toolValidator.validateRequest(request, "12345"));
        List<Error> errors = exception.getErrors();
        assertEquals(1, errors.size());
        assertEquals(ErrorType.RENTALDAYS_INVALID.name(), errors.get(0).getCode());
        assertEquals(Constants.RENTALDAYS_INVALID_MESSAGE, errors.get(0).getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), errors.get(0).getStatus());
    }

    @Test
    void validateRequest_InvalidDiscountPercent_ExceptionThrown() {
        CheckoutRequest request = new CheckoutRequest();
        request.setRentalDays(3);
        request.setDiscountPercent(150);

        BusinessException exception = assertThrows(BusinessException.class, () -> toolValidator.validateRequest(request, "12345"));
        List<Error> errors = exception.getErrors();
        assertEquals(1, errors.size());
        assertEquals(ErrorType.DISCOUNTPERCENT_INVALID.name(), errors.get(0).getCode());
        assertEquals(Constants.DISCOUNTPERCENT_INVALID_MESSAGE, errors.get(0).getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), errors.get(0).getStatus());
    }

    @Test
    void validateRequest_InvalidCheckOutDate_ExceptionThrown() {
        CheckoutRequest request = new CheckoutRequest();
        request.setRentalDays(2);
        request.setDiscountPercent(20);
        request.setCheckoutDate(null);

        BusinessException exception = assertThrows(BusinessException.class, () -> toolValidator.validateRequest(request, "12345"));
        List<Error> errors = exception.getErrors();
        assertEquals(1, errors.size());
        assertEquals(ErrorType.CHECKOUTDATE_INVALID.name(), errors.get(0).getCode());
        assertEquals(Constants.CHECKOUTDATE_INVALID_MESSAGE, errors.get(0).getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), errors.get(0).getStatus());
    }

    @Test
    void validateToolNotFound_ValidToolCode_NoException() {
        List<Error> errorList = new ArrayList<>();
        assertDoesNotThrow(() -> toolValidator.validateToolNotFound("LADW", errorList, "12345"));
    }

    @Test
    void validateToolNotFound_NullToolCode_NoException() {
        List<Error> errorList = new ArrayList<>();
        assertDoesNotThrow(() -> toolValidator.validateToolNotFound(null, errorList, "12345"));
    }

    @Test
    void validateToolNotFound_NonAlphabeticToolCode_ExceptionThrown() {
        List<Error> errorList = new ArrayList<>();
        assertDoesNotThrow(() -> toolValidator.validateToolNotFound("12345", errorList, "12345"));
        assertFalse(errorList.isEmpty());
        assertEquals(ErrorType.TOOL_NOT_FOUND.name(), errorList.get(0).getCode());
        assertEquals(Constants.TOOL_NOT_FOUND_MESSAGE, errorList.get(0).getMessage());
        assertEquals(HttpStatus.NOT_FOUND.value(), errorList.get(0).getStatus());
    }

    @Test
    public void testIsAlphabetic_ValidToolCode_ReturnsTrue() {
        ToolValidator validator = new ToolValidator();
        assertTrue(validator.isAlphabetic("TOOLCODE"));
    }

    @Test
    public void testIsAlphabetic_AlphanumericToolCode_ReturnsFalse() {
        ToolValidator validator = new ToolValidator();
        assertFalse(validator.isAlphabetic("TOOL123"));
    }

    @Test
    public void testIsAlphabetic_NonAlphabeticToolCode_ReturnsFalse() {
        ToolValidator validator = new ToolValidator();
        assertFalse(validator.isAlphabetic("12345"));
    }

    @Test
    public void testIsAlphabetic_EmptyToolCode_ReturnsTrue() {
        ToolValidator validator = new ToolValidator();
        assertTrue(validator.isAlphabetic(""));
    }
}