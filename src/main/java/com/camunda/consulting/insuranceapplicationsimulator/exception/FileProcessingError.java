package com.camunda.consulting.insuranceapplicationsimulator.exception;

public class FileProcessingError extends RuntimeException {

    public FileProcessingError(String message){
        super(message);
    }
}
