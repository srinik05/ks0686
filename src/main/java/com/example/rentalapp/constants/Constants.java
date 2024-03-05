package com.example.rentalapp.constants;

import org.springframework.stereotype.Component;

/**
 * This class defines constant messages used throughout the application.
 */
@Component
public class Constants {
    public static final String RENTALDAYS_INVALID_MESSAGE = "Rental day count must be 1 or greater.";
    public static final String DISCOUNTPERCENT_INVALID_MESSAGE = "Discount percent must be in the range 0-100.";
    public static final String TOOL_NOT_FOUND_MESSAGE = "Invalid tool code. Tool not found.";
    public static final String CHECKOUTDATE_INVALID_MESSAGE = "Date field is invalid, Please enter valid date.";
}
