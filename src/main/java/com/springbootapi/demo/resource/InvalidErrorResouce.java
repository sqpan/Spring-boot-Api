package com.springbootapi.demo.resource;

public class InvalidErrorResouce {

    private String message;

    private Object Errors;

    public InvalidErrorResouce(String message, Object errors) {
        this.message = message;
        Errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public Object getErrors() {
        return Errors;
    }
}
