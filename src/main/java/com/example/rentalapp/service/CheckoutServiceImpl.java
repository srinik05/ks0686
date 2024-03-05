package com.example.rentalapp.service;

import com.example.rentalapp.exception.BusinessException;
import com.example.rentalapp.exception.Error;
import com.example.rentalapp.exception.NotFoundException;
import com.example.rentalapp.model.CheckoutRequest;
import com.example.rentalapp.model.RentalAgreementResponse;
import com.example.rentalapp.model.Tool;
import com.example.rentalapp.util.ToolUtility;
import com.example.rentalapp.validator.ToolValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the CheckoutService interface.
 */
@Component
public class CheckoutServiceImpl implements CheckoutService{
    @Autowired
    private ToolValidator toolValidator;
    @Autowired
    private ToolUtility toolUtility;

    /**
     * Performs the checkout process for a tool based on the provided request.
     *
     * @param request  The checkout request containing tool information and checkout details.
     * @param traceId  The optional trace ID for tracking the request.
     * @return A RentalAgreementResponse object containing the rental agreement details.
     * @throws BusinessException if an error occurs during the checkout process.
     */
    public RentalAgreementResponse checkout(CheckoutRequest request, String traceId) throws BusinessException {
        List<Error> errorList = new ArrayList<>();
        errorList  = toolValidator.validateRequest(request, traceId);

        if(!errorList.isEmpty()){
            throw new BusinessException(errorList);
        }
        Tool tool = toolUtility.getTool(request.getToolCode());
        if(tool==null) {
            errorList = toolValidator.validateToolNotFound(request.getToolCode(), errorList, traceId);
            BusinessException be = new NotFoundException(errorList);
            throw be;
        }
        return toolUtility.calculateRentalAgreement(request.getToolCode(), tool.getToolType(), tool.getBrand(), request.getRentalDays(), request.getCheckoutDate(), request.getDiscountPercent());
    }
}
