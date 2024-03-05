package com.example.rentalapp.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckoutRequestTest {

    @Test
    public void testConstructorAndGetters() {
        // Arrange
        String toolCode = "LADW";
        int rentalDays = 5;
        int discountPercent = 10;
        LocalDate checkoutDate = LocalDate.of(2024, 3, 1);

        CheckoutRequest request = new CheckoutRequest(toolCode, rentalDays, discountPercent, checkoutDate);

        assertEquals(toolCode, request.getToolCode());
        assertEquals(rentalDays, request.getRentalDays());
        assertEquals(discountPercent, request.getDiscountPercent());
        assertEquals(checkoutDate, request.getCheckoutDate());
    }

    @Test
    public void testToString() {
        String toolCode = "LADW";
        int rentalDays = 5;
        int discountPercent = 10;
        LocalDate checkoutDate = LocalDate.of(2024, 3, 1);
        CheckoutRequest request = new CheckoutRequest(toolCode, rentalDays, discountPercent, checkoutDate);

        String toStringResult = request.toString();
        String expectedToString = "CheckoutRequest(toolCode=LADW, rentalDays=5, discountPercent=10, checkoutDate=2024-03-01)";
        assertEquals(expectedToString, toStringResult);
    }
}
