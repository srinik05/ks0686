package com.example.rentalapp.controller;

import com.example.rentalapp.exception.BusinessException;
import com.example.rentalapp.model.CheckoutRequest;
import com.example.rentalapp.service.CheckoutService;
import com.example.rentalapp.model.RentalAgreementResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling tool-related requests.
 */
@RestController
@RequestMapping("/api/tools")
public class AppRentalController {
    @Autowired
    private CheckoutService checkoutService;

    /**
     * Handles POST requests for checking out a tool.
     * @param request The checkout request containing tool information
     * @param traceId Optional trace ID for tracking requests
     * @return ResponseEntity containing RentalAgreementResponse with checkout details
     * @throws BusinessException If there is a business exception during checkout process
     */
    @PostMapping(value = "/checkout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RentalAgreementResponse> checkoutTool(@RequestBody CheckoutRequest request, @RequestHeader(name = "X-TRACE-ID", required = false) String traceId) throws BusinessException {
        RentalAgreementResponse rentalAgreement = checkoutService.checkout(request, traceId);
        return new ResponseEntity<>(rentalAgreement, HttpStatus.OK);
    }
}