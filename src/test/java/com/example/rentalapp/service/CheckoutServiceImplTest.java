package com.example.rentalapp.service;

import com.example.rentalapp.constants.ToolType;
import com.example.rentalapp.exception.BusinessException;
import com.example.rentalapp.exception.Error;
import com.example.rentalapp.model.CheckoutRequest;
import com.example.rentalapp.model.RentalAgreementResponse;
import com.example.rentalapp.model.Tool;
import com.example.rentalapp.util.ToolUtility;
import com.example.rentalapp.validator.ToolValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

public class CheckoutServiceImplTest {

    @Mock
    private ToolValidator toolValidator;

    @Mock
    private ToolUtility toolUtility;

    @InjectMocks
    private CheckoutServiceImpl checkoutService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void checkout_Success() throws BusinessException {
        CheckoutRequest request = new CheckoutRequest();
        request.setToolCode("LADW");
        request.setRentalDays(5);
        request.setDiscountPercent(10);
        request.setCheckoutDate(LocalDate.now());

        String traceId = "12345";

        List<Error> errorList = new ArrayList<>();

        when(toolValidator.validateRequest(request, traceId)).thenReturn(errorList);

        Tool tool = new Tool("LADW", ToolType.LADDER, "Werner");
        when(toolUtility.getTool(request.getToolCode())).thenReturn(tool);

        RentalAgreementResponse expectedResponse = new RentalAgreementResponse();
        when(toolUtility.calculateRentalAgreement(request.getToolCode(), tool.getToolType(), tool.getBrand(), request.getRentalDays(), request.getCheckoutDate(), request.getDiscountPercent())).thenReturn(expectedResponse);

        RentalAgreementResponse actualResponse = checkoutService.checkout(request, traceId);

        assertSame(expectedResponse, actualResponse);
    }
}

