package com.camunda.consulting.insuranceapplicationsimulator.exception;

public class BatchProcessingException extends Exception {

    public BatchProcessingException(String message){
        super(message);
    }

    public BatchProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
