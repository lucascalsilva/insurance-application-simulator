package com.camunda.consulting.insuranceapplicationsimulator.exception;

public class InsuranceAppServiceException extends Exception {

    public InsuranceAppServiceException(String message) {
        super(message);
    }

    public InsuranceAppServiceException(String message, Throwable cause){
        super(message, cause);
    }
}
