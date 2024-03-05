package com.example.rentalapp.model;

import com.example.rentalapp.constants.ToolType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Tool class represents a tool entity with attributes such as tool code, tool type, and brand.
 */
@AllArgsConstructor
@Getter
@ToString
public class Tool {
    private String toolCode;
    private ToolType toolType;
    private String brand;
}

