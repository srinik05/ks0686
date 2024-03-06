package com.example.rentalapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

/**
 * CheckoutRequest class represents the request object for checking out a tool.
 * It contains information such as tool code, rental days, discount percent, and checkout date.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CheckoutRequest {
    private String toolCode;
    private int rentalDays;
    private int discountPercent;
    @JsonFormat(pattern = "MM/dd/yy")
    private LocalDate checkoutDate;
}
