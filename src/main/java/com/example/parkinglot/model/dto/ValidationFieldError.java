package com.example.parkinglot.model.dto;


public class ValidationFieldError {
    private String fieldName;
    private String error;

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getError() {
        return error;
    }

    public ValidationFieldError(String fieldName, String error) {
        this.fieldName = fieldName;
        this.error = error;
    }
}
