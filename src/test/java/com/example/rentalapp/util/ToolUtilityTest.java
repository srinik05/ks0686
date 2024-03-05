package com.example.rentalapp.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.example.rentalapp.constants.ToolType;
import com.example.rentalapp.model.RentalAgreementResponse;
import com.example.rentalapp.model.Tool;

class ToolUtilityTest {

    @Test
    void testGetTool() {
        ToolUtility toolUtility = new ToolUtility();
        Tool expectedTool = new Tool("LADW", ToolType.LADDER, "Werner");

        Tool actualTool = toolUtility.getTool("LADW");
        assertEquals(expectedTool.getToolCode(), actualTool.getToolCode());

        actualTool = toolUtility.getTool("INVALID_TOOL_CODE");
        assertEquals(null, actualTool);
    }

    @Test
    void testCalculateRentalAgreement() {
        ToolUtility toolUtility = new ToolUtility();
        LocalDate checkoutDate = LocalDate.of(2024, 3, 1);
        RentalAgreementResponse expectedResponse = new RentalAgreementResponse(
                "LADW", ToolType.LADDER, "Werner", 5, checkoutDate, checkoutDate.plusDays(5),
                1.99, 5, 1.99 * 5, 10, 1.99 * 5 * 10 / 100, 1.99 * 5 * 90 / 100
        );

        RentalAgreementResponse actualResponse = toolUtility.calculateRentalAgreement(
                "LADW", ToolType.LADDER, "Werner", 5, checkoutDate, 10
        );
        assertEquals(expectedResponse.getFinalCharge(), actualResponse.getFinalCharge(), 0.001);
    }

    @Test
    void testIsHoliday() {
        ToolUtility toolUtility = new ToolUtility();

        LocalDate independenceDay = LocalDate.of(2024, 7, 4);
        assertTrue(toolUtility.isHoliday(independenceDay));

        LocalDate laborDay = LocalDate.of(2024, 9, 2);
        assertTrue(toolUtility.isHoliday(laborDay));

        LocalDate normalDay = LocalDate.of(2024, 6, 1);
        assertFalse(toolUtility.isHoliday(normalDay));
    }
}

