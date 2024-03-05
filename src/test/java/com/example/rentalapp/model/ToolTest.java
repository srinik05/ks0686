package com.example.rentalapp.model;

import com.example.rentalapp.constants.ToolType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToolTest {

    @Test
    public void testConstructorAndGetters() {
        String toolCode = "LADW";
        ToolType toolType = ToolType.LADDER;
        String brand = "Werner";

        Tool tool = new Tool(toolCode, toolType, brand);

        assertEquals(toolCode, tool.getToolCode());
        assertEquals(toolType, tool.getToolType());
        assertEquals(brand, tool.getBrand());
    }

    @Test
    public void testToString() {
        String toolCode = "LADW";
        ToolType toolType = ToolType.LADDER;
        String brand = "Werner";
        Tool tool = new Tool(toolCode, toolType, brand);

        String toStringResult = tool.toString();

        String expectedToString = "Tool(toolCode=LADW, toolType=LADDER, brand=Werner)";
        assertEquals(expectedToString, toStringResult);
    }
}