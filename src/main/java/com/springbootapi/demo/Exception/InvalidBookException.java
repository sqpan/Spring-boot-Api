package com.springbootapi.demo.Exception;

import org.springframework.validation.Errors;

public class InvalidBookException extends RuntimeException{

    private Errors errors;

    public InvalidBookException(String message, Errors errors) {
        super(message);
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
