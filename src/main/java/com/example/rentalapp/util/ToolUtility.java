package com.example.rentalapp.util;

import com.example.rentalapp.constants.ToolEntry;
import com.example.rentalapp.constants.ToolType;
import com.example.rentalapp.model.Tool;
import com.example.rentalapp.model.RentalAgreementResponse;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for tool-related operations.
 */
@Component
public class ToolUtility {

    private static Map<String, Tool> tools = new HashMap<>();

    // Initialize tools map with pre-defined tool entries
   public ToolUtility() {
        for (ToolEntry entry : ToolEntry.values()) {
            tools.put(entry.getToolCode(), new Tool(entry.getToolCode(), entry.getToolType(), entry.getBrand()));
        }
    }

    /**
     * Retrieves a tool based on the provided tool code.
     *
     * @param toolCode The code of the tool to retrieve.
     * @return The Tool object corresponding to the tool code.
     */
    public Tool getTool(String toolCode) {
        return tools.get(toolCode);
    }
    /**
     * Calculates the rental agreement for a tool based on the provided parameters.
     *
     * @param toolCode        The code of the tool.
     * @param toolType        The type of the tool.
     * @param toolBrand       The brand of the tool.
     * @param rentalDays      The number of rental days.
     * @param checkoutDate    The checkout date.
     * @param discountPercent The discount percentage.
     * @return A RentalAgreementResponse object containing the rental agreement details.
     */
    public RentalAgreementResponse calculateRentalAgreement(String toolCode, ToolType toolType, String toolBrand,
                                                            int rentalDays, LocalDate checkoutDate, int discountPercent) {
        double dailyRentalCharge = toolType.getDailyCharge();
        LocalDate dueDate = calculateDueDate(checkoutDate, rentalDays);
        int chargeDays = calculateChargeDays(checkoutDate, dueDate, toolType);
        double preDiscountCharge = calculatePreDiscountCharge(chargeDays, dailyRentalCharge);
        double discountAmount = calculateDiscountAmount(preDiscountCharge, discountPercent);
        double finalCharge = calculateFinalCharge(preDiscountCharge, discountAmount);

        return new RentalAgreementResponse(toolCode, toolType, toolBrand, rentalDays, checkoutDate, dueDate, dailyRentalCharge,
                chargeDays, preDiscountCharge, discountPercent, discountAmount, finalCharge);
    }

    /**
     * Calculates the due date based on the checkout date and rental days.
     *
     * @param checkoutDate The checkout date.
     * @param rentalDays   The number of rental days.
     * @return The due date.
     */
    private LocalDate calculateDueDate(LocalDate checkoutDate, int rentalDays) {
        return checkoutDate.plusDays(rentalDays);
    }

    /**
     * Calculates the number of chargeable days between the checkout date and due date.
     *
     * @param checkoutDate The checkout date.
     * @param dueDate      The due date.
     * @param toolType     The type of the tool.
     * @return The number of chargeable days.
     */
    private int calculateChargeDays(LocalDate checkoutDate, LocalDate dueDate, ToolType toolType) {
        int count = 0;
        LocalDate currentDate = checkoutDate.plusDays(1);
        while (!currentDate.isAfter(dueDate)) {
            if (isChargeableDay(currentDate, toolType) && !isHoliday(currentDate)) {
                count++;
            }
            currentDate = currentDate.plusDays(1);
        }
        return count;
    }

    /**
     * Checks if a given date is a chargeable day based on the tool type.
     *
     * @param date     The date to check.
     * @param toolType The type of the tool.
     * @return true if the date is a chargeable day, false otherwise.
     */
    private boolean isChargeableDay(LocalDate date, ToolType toolType) {
        return (toolType.isWeekdayCharge() && date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) ||
                (toolType.isWeekendCharge() && (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)) ||
                (toolType.isHolidayCharge() && isHoliday(date));
    }

    /**
     * Calculates the pre-discount charge based on the number of chargeable days and daily rental charge.
     *
     * @param chargeDays       The number of chargeable days.
     * @param dailyRentalCharge The daily rental charge.
     * @return The pre-discount charge.
     */
    private double calculatePreDiscountCharge(int chargeDays, double dailyRentalCharge) {
        return chargeDays * dailyRentalCharge;
    }

    /**
     * Calculates the discount amount based on the pre-discount charge and discount percentage.
     *
     * @param preDiscountCharge The pre-discount charge.
     * @param discountPercent   The discount percentage.
     * @return The discount amount.
     */
    private double calculateDiscountAmount(double preDiscountCharge, int discountPercent) {
        return (preDiscountCharge * discountPercent) / 100.0;
    }

    /**
     * Calculates the final charge after applying the discount.
     *
     * @param preDiscountCharge The pre-discount charge.
     * @param discountAmount    The discount amount.
     * @return The final charge.
     */
    private double calculateFinalCharge(double preDiscountCharge, double discountAmount) {
        return preDiscountCharge - discountAmount;
    }

    /**
     * Checks if a given date is a holiday (Independence Day or Labor Day).
     *
     * @param date The date to check.
     * @return true if the date is a holiday, false otherwise.
     */
    public boolean isHoliday(LocalDate date) {
        return isIndependenceDay(date) || isLaborDay(date);
    }

    /**
     * Checks if a given date is Independence Day (July 4th).
     *
     * @param date The date to check.
     * @return true if the date is Independence Day, false otherwise.
     */
    private boolean isIndependenceDay(LocalDate date) {
        return date.getMonthValue() == 7 && date.getDayOfMonth() == 4;
    }

    /**
     * Checks if a given date is Labor Day (first Monday in September).
     *
     * @param date The date to check.
     * @return true if the date is Labor Day, false otherwise.
     */
    private  boolean isLaborDay(LocalDate date) {
        return (date.getMonthValue() == 9 && date.getDayOfWeek() == DayOfWeek.MONDAY && date.getDayOfMonth() <= 7);
    }
}
