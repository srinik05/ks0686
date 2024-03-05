package com.example.rentalapp.model;

import com.example.rentalapp.constants.ToolType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * RentalAgreementResponse class represents the response object for a rental agreement.
 * It contains details such as tool code, tool type, tool brand, rental days, checkout date,
 * due date, daily rental charge, chargeable days, pre-discount charge, discount percent,
 * discount amount, and final charge.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RentalAgreementResponse {
    @JsonProperty("toolCode")
    private String toolCode;
    @JsonProperty("toolType")
    private ToolType toolType;
    @JsonProperty("toolBrand")
    private String toolBrand;
    @JsonProperty("rentalDays")
    private int rentalDays;
    @JsonProperty("checkoutDate")
    private LocalDate checkoutDate;
    @JsonProperty("dueDate")
    private LocalDate dueDate;
    @JsonProperty("dailyRentalCharge")
    private double dailyRentalCharge;
    @JsonProperty("chargeDays")
    private int chargeDays;
    @JsonProperty("preDiscountCharge")
    private double preDiscountCharge;
    @JsonProperty("discountPercent")
    private int discountPercent;
    @JsonProperty("discountAmount")
    private double discountAmount;
    @JsonProperty("finalCharge")
    private double finalCharge;
}
