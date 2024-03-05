package com.example.rentalapp.service;


import com.example.rentalapp.exception.BusinessException;
import com.example.rentalapp.model.CheckoutRequest;
import com.example.rentalapp.model.RentalAgreementResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * CheckoutService interface defines the contract for checking out a tool.
 */
@Service
public interface CheckoutService {

    /**
     * Performs the checkout process for a tool based on the provided request.
     *
     * @param request  The checkout request containing tool information and checkout details.
     * @param traceId  The optional trace ID for tracking the request.
     * @return A RentalAgreementResponse object containing the rental agreement details.
     * @throws BusinessException if an error occurs during the checkout process.
     */
    RentalAgreementResponse checkout(CheckoutRequest request, String traceId) throws BusinessException;
}


