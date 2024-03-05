package com.example.rentalapp.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing different tools and their details.
 */
@Getter
@AllArgsConstructor
public enum ToolEntry {
    LADW("LADW", ToolType.LADDER, "Werner"),
    CHNS("CHNS", ToolType.CHAINSAW, "Stihl"),
    JAKD("JAKD", ToolType.JACKHAMMER, "DeWalt"),
    JAKR("JAKR", ToolType.JACKHAMMER, "Ridgid");

    private final String toolCode;
    private final ToolType toolType;
    private final String brand;
}
