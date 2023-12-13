package com.example.parkinglot.model.dto;

import java.util.ArrayList;
import java.util.List;


public class CustomValidationException extends RuntimeException{

    private List<String> globalErrors = new ArrayList<>();
    private List<ValidationFieldError> fieldErrors = new ArrayList<>();

    public void setGlobalErrors(List<String> globalErrors) {
        this.globalErrors = globalErrors;
    }

    public CustomValidationException(String message, List<String> globalErrors, List<ValidationFieldError> fieldErrors) {
        super(message);
        this.globalErrors = globalErrors;
        this.fieldErrors = fieldErrors;
    }

    public CustomValidationException() {
    }

    public void setFieldErrors(List<ValidationFieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public List<String> getGlobalErrors() {
        return globalErrors;
    }

    public List<ValidationFieldError> getFieldErrors() {
        return fieldErrors;
    }

    public CustomValidationException(String message) {
        super(message);
        globalErrors.add(message);
    }

    public CustomValidationException(String fieldName, String error) {
        super(error);
        fieldErrors.add(new ValidationFieldError(fieldName, error));
    }
}
