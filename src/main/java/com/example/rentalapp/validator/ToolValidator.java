package com.example.rentalapp.validator;

import com.example.rentalapp.constants.Constants;
import com.example.rentalapp.constants.ErrorType;
import com.example.rentalapp.exception.BusinessException;
import com.example.rentalapp.exception.Error;
import com.example.rentalapp.model.CheckoutRequest;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Validator class for tool-related operations.
 */
@Component
@Slf4j
public class ToolValidator {

    /**
     * Validates the request parameters for rental days and discount percent.
     *
     * @param request     The number of rental days and  The discount percentage.
     * @param traceId        The trace ID for logging purposes.
     * @return A list of errors, if any.
     * @throws BusinessException If the rental days or discount percent are invalid.
     */
    public List<Error> validateRequest(CheckoutRequest request, String traceId) throws BusinessException {

        Error error = null;
        List<Error> errorList = new ArrayList<>();
        if (request.getRentalDays() < 1) {
            error = new Error();
            error.setCode(ErrorType.RENTALDAYS_INVALID.name());
            error.setMessage(Constants.RENTALDAYS_INVALID_MESSAGE);
            error.setStatus(HttpStatus.BAD_REQUEST.value());
            error.setTraceId(StringUtils.isBlank(traceId) ? error.getTraceId() : traceId);
            errorList.add(error);
            throw  new BusinessException(errorList);
        }
        if (request.getDiscountPercent() < 0 || request.getDiscountPercent() > 100) {
            error = new Error();
            error.setCode(ErrorType.DISCOUNTPERCENT_INVALID.name());
            error.setMessage(Constants.DISCOUNTPERCENT_INVALID_MESSAGE);
            error.setStatus(HttpStatus.BAD_REQUEST.value());
            error.setTraceId(StringUtils.isBlank(traceId) ? error.getTraceId() : traceId);
            errorList.add(error);
            throw  new BusinessException(errorList);
        }
        if (request.getCheckoutDate() == null) {
            error = new Error();
            error.setCode(ErrorType.CHECKOUTDATE_INVALID.name());
            error.setMessage(Constants.CHECKOUTDATE_INVALID_MESSAGE);
            error.setStatus(HttpStatus.BAD_REQUEST.value());
            error.setTraceId(StringUtils.isBlank(traceId) ? error.getTraceId() : traceId);
            errorList.add(error);
            throw  new BusinessException(errorList);
        }
        return errorList;
    }
    /**
     * Validates if the provided tool code is not found.
     *
     * @param toolCode  The tool code to validate.
     * @param errorList The list of errors to append any validation errors to.
     * @param traceId   The trace ID for logging purposes.
     * @return The updated error list.
     */
    public List<Error> validateToolNotFound(String toolCode, List<Error> errorList, String traceId) {
        Error error = null;
        new ArrayList<>();
        if(toolCode!=null || toolCode == null ||  !this.isAlphabetic(toolCode)){
            error = new Error();
            error.setCode(ErrorType.TOOL_NOT_FOUND.name());
            error.setMessage(Constants.TOOL_NOT_FOUND_MESSAGE);
            error.setStatus(HttpStatus.NOT_FOUND.value());
            error.setTraceId(StringUtils.isBlank(traceId) ? error.getTraceId() : traceId);
            errorList.add(error);
        }
        return errorList;
    }

    public boolean isAlphabetic(String toolCode){
        final int len = toolCode.length();
        for(int i=0; i<len; i++){
            if(!Character.isAlphabetic(toolCode.charAt(i))){
                return false;
            }
        }
        return true;
    }
}