package com.example.rentalapp.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing different types of tools along with their properties.
 */
@Getter
@AllArgsConstructor
public enum ToolType {
    LADDER("Ladder", 1.99, true, true, false),
    CHAINSAW("Chainsaw", 1.49, true, false, true),
    JACKHAMMER("Jackhammer", 2.99, true, false, false);

    private final String type;
    private final double dailyCharge;
    private final boolean weekdayCharge;
    private final boolean weekendCharge;
    private final boolean holidayCharge;
}
