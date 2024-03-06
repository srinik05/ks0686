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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
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


    //checkout success case
    @Test
    void checkout_Success() throws BusinessException {
        CheckoutRequest request = new CheckoutRequest();
        request.setToolCode("LADW");
        request.setRentalDays(5);
        request.setDiscountPercent(10);
        request.setCheckoutDate(LocalDate.now());

        List<Error> errorList = new ArrayList<>();
        when(toolValidator.validateRequest(request, UUID.randomUUID().toString())).thenReturn(errorList);

        Tool tool = new Tool("LADW", ToolType.LADDER, "Werner");
        when(toolUtility.getTool(request.getToolCode())).thenReturn(tool);

        RentalAgreementResponse expectedResponse = new RentalAgreementResponse();
        expectedResponse.setToolCode("LADW");
        expectedResponse.setToolType(ToolType.LADDER);
        expectedResponse.setToolBrand("Werner");
        when(toolUtility.calculateRentalAgreement(request.getToolCode(), tool.getToolType(), tool.getBrand(), request.getRentalDays(), request.getCheckoutDate(), request.getDiscountPercent())).thenReturn(expectedResponse);

        RentalAgreementResponse actualResponse = checkoutService.checkout(request, UUID.randomUUID().toString());

        assertSame(expectedResponse, actualResponse);
        assertEquals("LADW", actualResponse.getToolCode());
        assertEquals(ToolType.LADDER, actualResponse.getToolType());
        assertEquals("Werner", actualResponse.getToolBrand());
    }

    // test for Invalid Discount Percent
    /*Tool code	    JAKR
    Checkout date	9/3/15
    Rental days 	5
    Discount	    101%*/

    @Test
    void testInvalidDiscountPercent() {
        CheckoutRequest request = new CheckoutRequest();
        request.setToolCode("JAKR");
        request.setRentalDays(5);
        request.setDiscountPercent(101);
        request.setCheckoutDate(LocalDate.parse("09/03/15", DateTimeFormatter.ofPattern("MM/dd/yy")));

        assertThrows(BusinessException.class, () -> {
            checkoutService.checkout(request, UUID.randomUUID().toString());
        });

    }

   // test for Ladder Checkout With Discount
   /*  Tool code		LADW
    Checkout date	    7/2/20
    Rental days	        3
    Discount		    10%*/

    @Test
    void testLadderCheckoutWithDiscount() throws BusinessException {

        CheckoutRequest request = new CheckoutRequest();
        request.setToolCode("LADW");
        request.setRentalDays(3);
        request.setDiscountPercent(10);
        request.setCheckoutDate(LocalDate.parse("07/02/20", DateTimeFormatter.ofPattern("MM/dd/yy")));

        Tool tool = new Tool("LADW", ToolType.LADDER, "Werner");
        when(toolUtility.getTool(request.getToolCode())).thenReturn(tool);

        RentalAgreementResponse expectedResponse = new RentalAgreementResponse();
        expectedResponse.setToolCode("LADW");
        expectedResponse.setToolType(ToolType.LADDER);
        expectedResponse.setToolBrand("Werner");
        expectedResponse.setRentalDays(3);
        expectedResponse.setCheckoutDate(LocalDate.parse("07/02/20", DateTimeFormatter.ofPattern("MM/dd/yy")));
        expectedResponse.setDueDate(LocalDate.parse("07/05/20", DateTimeFormatter.ofPattern("MM/dd/yy")));
        expectedResponse.setDailyRentalCharge(1.99);
        expectedResponse.setChargeDays(2);
        expectedResponse.setPreDiscountCharge(3.98);
        expectedResponse.setDiscountPercent(10);
        expectedResponse.setDiscountAmount(0.40);
        expectedResponse.setFinalCharge(3.58);

        when(toolUtility.calculateRentalAgreement(request.getToolCode(), tool.getToolType(), tool.getBrand(), request.getRentalDays(), request.getCheckoutDate(), request.getDiscountPercent())).thenReturn(expectedResponse);
        RentalAgreementResponse actualResponse = checkoutService.checkout(request, UUID.randomUUID().toString());

        assertSame(expectedResponse, actualResponse);
        assertEquals("LADW", actualResponse.getToolCode());
        assertEquals(ToolType.LADDER, actualResponse.getToolType());
        assertEquals("Werner", actualResponse.getToolBrand());
        assertEquals(3, actualResponse.getRentalDays());
        assertEquals("07/02/20", actualResponse.getCheckoutDate().format(DateTimeFormatter.ofPattern("MM/dd/yy")));
        assertEquals("07/05/20", actualResponse.getDueDate().format(DateTimeFormatter.ofPattern("MM/dd/yy")));
        assertEquals("$1.99", String.format("$%.2f", actualResponse.getDailyRentalCharge()));
        assertEquals(2, actualResponse.getChargeDays());
        assertEquals("$3.98", String.format("$%.2f", actualResponse.getPreDiscountCharge()));
        assertEquals(10, actualResponse.getDiscountPercent());
        assertEquals("$0.40", String.format("$%.2f", actualResponse.getDiscountAmount()));
        assertEquals("$3.58", String.format("$%.2f", actualResponse.getFinalCharge()));
    }


    // test for Chainsaw Checkout With Discount
   /*  Tool code		CHNS
    Checkout date	    7/2/15
    Rental days	        5
    Discount		    25%*/

    @Test
    void testChainsawCheckoutWithDiscount() throws BusinessException {
        CheckoutRequest request = new CheckoutRequest();
        request.setToolCode("CHNS");
        request.setRentalDays(5);
        request.setDiscountPercent(25);
        request.setCheckoutDate(LocalDate.parse("07/02/15", DateTimeFormatter.ofPattern("MM/dd/yy")));

        Tool tool = new Tool("CHNS", ToolType.CHAINSAW, "Stihl");
        when(toolUtility.getTool(request.getToolCode())).thenReturn(tool);

        RentalAgreementResponse expectedResponse = new RentalAgreementResponse();
        expectedResponse.setToolCode("CHNS");
        expectedResponse.setToolType(ToolType.CHAINSAW);
        expectedResponse.setToolBrand("Stihl");
        expectedResponse.setRentalDays(5);
        expectedResponse.setCheckoutDate(LocalDate.parse("07/02/15", DateTimeFormatter.ofPattern("MM/dd/yy")));
        expectedResponse.setDueDate(LocalDate.parse("07/07/15", DateTimeFormatter.ofPattern("MM/dd/yy")));
        expectedResponse.setDailyRentalCharge(1.49);
        expectedResponse.setChargeDays(3);
        expectedResponse.setPreDiscountCharge(4.47);
        expectedResponse.setDiscountPercent(25);
        expectedResponse.setDiscountAmount(1.12);
        expectedResponse.setFinalCharge(3.35);

        when(toolUtility.calculateRentalAgreement(request.getToolCode(), tool.getToolType(), tool.getBrand(), request.getRentalDays(), request.getCheckoutDate(), request.getDiscountPercent())).thenReturn(expectedResponse);
        RentalAgreementResponse actualResponse = checkoutService.checkout(request, UUID.randomUUID().toString());

        assertEquals("CHNS", actualResponse.getToolCode());
        assertEquals(ToolType.CHAINSAW, actualResponse.getToolType());
        assertEquals("Stihl", actualResponse.getToolBrand());
        assertEquals(5, actualResponse.getRentalDays());
        assertEquals("07/02/15", actualResponse.getCheckoutDate().format(DateTimeFormatter.ofPattern("MM/dd/yy")));
        assertEquals("07/07/15", actualResponse.getDueDate().format(DateTimeFormatter.ofPattern("MM/dd/yy")));
        assertEquals("$1.49", String.format("$%.2f", actualResponse.getDailyRentalCharge()));
        assertEquals(3, actualResponse.getChargeDays());
        assertEquals("$4.47", String.format("$%.2f", actualResponse.getPreDiscountCharge()));
        assertEquals(25, actualResponse.getDiscountPercent());
        assertEquals("$1.12", String.format("$%.2f", actualResponse.getDiscountAmount()));
        assertEquals("$3.35", String.format("$%.2f", actualResponse.getFinalCharge()));
    }

    // test for Jack hammer Checkout No Discount
    /*Tool code     JAKD
    Checkout date   9/3/15
    Rental days     6
    Discount        0%*/

    @Test
    void testJackhammerCheckoutNoDiscount() throws BusinessException {
        CheckoutRequest request = new CheckoutRequest();
        request.setToolCode("JAKD");
        request.setRentalDays(6);
        request.setDiscountPercent(0);
        request.setCheckoutDate(LocalDate.parse("09/03/15", DateTimeFormatter.ofPattern("MM/dd/yy")));

        Tool tool = new Tool("JAKD", ToolType.JACKHAMMER, "DeWalt");
        when(toolUtility.getTool(request.getToolCode())).thenReturn(tool);

        RentalAgreementResponse expectedResponse = new RentalAgreementResponse();
        expectedResponse.setToolCode("JAKD");
        expectedResponse.setToolType(ToolType.JACKHAMMER);
        expectedResponse.setToolBrand("DeWalt");
        expectedResponse.setRentalDays(6);
        expectedResponse.setCheckoutDate(LocalDate.parse("09/03/15", DateTimeFormatter.ofPattern("MM/dd/yy")));
        expectedResponse.setDueDate(LocalDate.parse("09/09/15", DateTimeFormatter.ofPattern("MM/dd/yy")));
        expectedResponse.setDailyRentalCharge(2.99);
        expectedResponse.setChargeDays(3);
        expectedResponse.setPreDiscountCharge(8.97);
        expectedResponse.setDiscountPercent(0);
        expectedResponse.setDiscountAmount(0.00);
        expectedResponse.setFinalCharge(8.97);


        when(toolUtility.calculateRentalAgreement(request.getToolCode(), tool.getToolType(), tool.getBrand(), request.getRentalDays(), request.getCheckoutDate(), request.getDiscountPercent())).thenReturn(expectedResponse);
        RentalAgreementResponse actualResponse = checkoutService.checkout(request, UUID.randomUUID().toString());


        assertEquals("JAKD", actualResponse.getToolCode());
        assertEquals(ToolType.JACKHAMMER, actualResponse.getToolType());
        assertEquals("DeWalt", actualResponse.getToolBrand());
        assertEquals(6, actualResponse.getRentalDays());
        assertEquals("09/03/15", actualResponse.getCheckoutDate().format(DateTimeFormatter.ofPattern("MM/dd/yy")));
        assertEquals("09/09/15", actualResponse.getDueDate().format(DateTimeFormatter.ofPattern("MM/dd/yy")));
        assertEquals("$2.99", String.format("$%.2f", actualResponse.getDailyRentalCharge()));
        assertEquals(3, actualResponse.getChargeDays());
        assertEquals("$8.97", String.format("$%.2f", actualResponse.getPreDiscountCharge()));
        assertEquals(0, actualResponse.getDiscountPercent());
        assertEquals("$0.00", String.format("$%.2f", actualResponse.getDiscountAmount()));
        assertEquals("$8.97", String.format("$%.2f", actualResponse.getFinalCharge()));

    }


    // test for Jack hammer Checkout No Discount More Days
    /*Tool code     JAKR
    Checkout date   7/2/15
    Rental days     9
    Discount        0%
    */

    @Test
    void testJackhammerCheckoutNoDiscountMoreDays() throws BusinessException {
        CheckoutRequest request = new CheckoutRequest();
        request.setToolCode("JAKR");
        request.setRentalDays(9);
        request.setDiscountPercent(0);
        request.setCheckoutDate(LocalDate.parse("07/02/15", DateTimeFormatter.ofPattern("MM/dd/yy")));

        Tool tool = new Tool("JAKD", ToolType.JACKHAMMER, "Ridgid");
        when(toolUtility.getTool(request.getToolCode())).thenReturn(tool);

        RentalAgreementResponse expectedResponse = new RentalAgreementResponse();
        expectedResponse.setToolCode("JAKD");
        expectedResponse.setToolType(ToolType.JACKHAMMER);
        expectedResponse.setToolBrand("Ridgid");
        expectedResponse.setRentalDays(9);
        expectedResponse.setCheckoutDate(LocalDate.parse("07/02/15", DateTimeFormatter.ofPattern("MM/dd/yy")));
        expectedResponse.setDueDate(LocalDate.parse("07/11/15", DateTimeFormatter.ofPattern("MM/dd/yy")));
        expectedResponse.setDailyRentalCharge(2.99);
        expectedResponse.setChargeDays(6);
        expectedResponse.setPreDiscountCharge(17.94);
        expectedResponse.setDiscountPercent(0);
        expectedResponse.setDiscountAmount(0.00);
        expectedResponse.setFinalCharge(17.94);


        when(toolUtility.calculateRentalAgreement(request.getToolCode(), tool.getToolType(), tool.getBrand(), request.getRentalDays(), request.getCheckoutDate(), request.getDiscountPercent())).thenReturn(expectedResponse);
        RentalAgreementResponse actualResponse = checkoutService.checkout(request, UUID.randomUUID().toString());


        assertEquals("JAKD", actualResponse.getToolCode());
        assertEquals(ToolType.JACKHAMMER, actualResponse.getToolType());
        assertEquals("Ridgid", actualResponse.getToolBrand());
        assertEquals(9, actualResponse.getRentalDays());
        assertEquals("07/02/15", actualResponse.getCheckoutDate().format(DateTimeFormatter.ofPattern("MM/dd/yy")));
        assertEquals("07/11/15", actualResponse.getDueDate().format(DateTimeFormatter.ofPattern("MM/dd/yy")));
        assertEquals("$2.99", String.format("$%.2f", actualResponse.getDailyRentalCharge()));
        assertEquals(6, actualResponse.getChargeDays());
        assertEquals("$17.94", String.format("$%.2f", actualResponse.getPreDiscountCharge()));
        assertEquals(0, actualResponse.getDiscountPercent());
        assertEquals("$0.00", String.format("$%.2f", actualResponse.getDiscountAmount()));
        assertEquals("$17.94", String.format("$%.2f", actualResponse.getFinalCharge()));
    }


    // test for Jack hammer Checkout With 50Percent Discount
    /*Tool code     JAKR
    Checkout date   7/2/20
    Rental days     4
    Discount        50%
    */

    @Test
    void testJackhammerCheckoutWith50PercentDiscount() throws BusinessException {

        CheckoutRequest request = new CheckoutRequest();
        request.setToolCode("JAKR");
        request.setRentalDays(4);
        request.setDiscountPercent(50);
        request.setCheckoutDate(LocalDate.parse("07/02/20", DateTimeFormatter.ofPattern("MM/dd/yy")));

        Tool tool = new Tool("JAKD", ToolType.JACKHAMMER, "Ridgid");
        when(toolUtility.getTool(request.getToolCode())).thenReturn(tool);


        RentalAgreementResponse expectedResponse = new RentalAgreementResponse();
        expectedResponse.setToolCode("JAKD");
        expectedResponse.setToolType(ToolType.JACKHAMMER);
        expectedResponse.setToolBrand("Ridgid");
        expectedResponse.setRentalDays(4);
        expectedResponse.setCheckoutDate(LocalDate.parse("07/02/20", DateTimeFormatter.ofPattern("MM/dd/yy")));
        expectedResponse.setDueDate(LocalDate.parse("07/06/20", DateTimeFormatter.ofPattern("MM/dd/yy")));
        expectedResponse.setDailyRentalCharge(2.99);
        expectedResponse.setChargeDays(2);
        expectedResponse.setPreDiscountCharge(9.98);
        expectedResponse.setDiscountPercent(50);
        expectedResponse.setDiscountAmount(2.99);
        expectedResponse.setFinalCharge(2.99);


        when(toolUtility.calculateRentalAgreement(request.getToolCode(), tool.getToolType(), tool.getBrand(), request.getRentalDays(), request.getCheckoutDate(), request.getDiscountPercent())).thenReturn(expectedResponse);
        RentalAgreementResponse actualResponse = checkoutService.checkout(request, UUID.randomUUID().toString());


        assertEquals("JAKD", actualResponse.getToolCode());
        assertEquals(ToolType.JACKHAMMER, actualResponse.getToolType());
        assertEquals("Ridgid", actualResponse.getToolBrand());
        assertEquals(4, actualResponse.getRentalDays());
        assertEquals("07/02/20", actualResponse.getCheckoutDate().format(DateTimeFormatter.ofPattern("MM/dd/yy")));
        assertEquals("07/06/20", actualResponse.getDueDate().format(DateTimeFormatter.ofPattern("MM/dd/yy")));
        assertEquals("$2.99", String.format("$%.2f", actualResponse.getDailyRentalCharge()));
        assertEquals(2, actualResponse.getChargeDays());
        assertEquals("$9.98", String.format("$%.2f", actualResponse.getPreDiscountCharge()));
        assertEquals(50, actualResponse.getDiscountPercent());
        assertEquals("$2.99", String.format("$%.2f", actualResponse.getDiscountAmount()));
        assertEquals("$2.99", String.format("$%.2f", actualResponse.getFinalCharge()));
    }
}



