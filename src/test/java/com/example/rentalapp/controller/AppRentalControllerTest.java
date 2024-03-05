package com.example.rentalapp.controller;

import com.example.rentalapp.exception.BusinessException;
import com.example.rentalapp.model.CheckoutRequest;
import com.example.rentalapp.model.RentalAgreementResponse;
import com.example.rentalapp.service.CheckoutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AppRentalControllerTest {

    @Mock
    private CheckoutService checkoutService;

    @InjectMocks
    private AppRentalController appRentalController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCheckoutTool_Success() throws BusinessException {
        CheckoutRequest request = new CheckoutRequest();
        String traceId = "12345";
        RentalAgreementResponse rentalAgreement = new RentalAgreementResponse();
        when(checkoutService.checkout(request, traceId)).thenReturn(rentalAgreement);

        ResponseEntity<RentalAgreementResponse> responseEntity = appRentalController.checkoutTool(request, traceId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(rentalAgreement, responseEntity.getBody());
    }

}
