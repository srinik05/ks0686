package com.example.rentalapp.model;

import com.example.rentalapp.constants.ToolType;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RentalAgreementResponseTest {

    @Test
    public void testConstructorAndGetters() {
        String toolCode = "LADW";
        ToolType toolType = ToolType.LADDER;
        String toolBrand = "Werner";
        int rentalDays = 5;
        LocalDate checkoutDate = LocalDate.of(2024, 3, 1);
        LocalDate dueDate = LocalDate.of(2024, 3, 6);
        double dailyRentalCharge = 1.99;
        int chargeDays = 5;
        double preDiscountCharge = 9.95;
        int discountPercent = 10;
        double discountAmount = 0.995;
        double finalCharge = 8.955;

        RentalAgreementResponse response = new RentalAgreementResponse(toolCode, toolType, toolBrand, rentalDays,
                checkoutDate, dueDate, dailyRentalCharge, chargeDays, preDiscountCharge, discountPercent,
                discountAmount, finalCharge);

        assertEquals(toolCode, response.getToolCode());
        assertEquals(toolType, response.getToolType());
        assertEquals(toolBrand, response.getToolBrand());
        assertEquals(rentalDays, response.getRentalDays());
        assertEquals(checkoutDate, response.getCheckoutDate());
        assertEquals(dueDate, response.getDueDate());
        assertEquals(dailyRentalCharge, response.getDailyRentalCharge());
        assertEquals(chargeDays, response.getChargeDays());
        assertEquals(preDiscountCharge, response.getPreDiscountCharge());
        assertEquals(discountPercent, response.getDiscountPercent());
        assertEquals(discountAmount, response.getDiscountAmount());
        assertEquals(finalCharge, response.getFinalCharge());
    }

    @Test
    public void testToString() {
        String toolCode = "LADW";
        ToolType toolType = ToolType.LADDER;
        String toolBrand = "Werner";
        int rentalDays = 5;
        LocalDate checkoutDate = LocalDate.of(2024, 3, 1);
        LocalDate dueDate = LocalDate.of(2024, 3, 6);
        double dailyRentalCharge = 1.99;
        int chargeDays = 5;
        double preDiscountCharge = 9.95;
        int discountPercent = 10;
        double discountAmount = 0.995;
        double finalCharge = 8.955;
        RentalAgreementResponse response = new RentalAgreementResponse(toolCode, toolType, toolBrand, rentalDays,
                checkoutDate, dueDate, dailyRentalCharge, chargeDays, preDiscountCharge, discountPercent,
                discountAmount, finalCharge);

        String toStringResult = response.toString();

        String expectedToString = "RentalAgreementResponse(toolCode=LADW, toolType=LADDER, toolBrand=Werner, rentalDays=5, checkoutDate=2024-03-01, dueDate=2024-03-06, dailyRentalCharge=1.99, chargeDays=5, preDiscountCharge=9.95, discountPercent=10, discountAmount=0.995, finalCharge=8.955)";
        assertEquals(expectedToString, toStringResult);
    }
}
