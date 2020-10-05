package com.springbootapi.demo.resource;

public class ErrorResult {

    private String message;

    public ErrorResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorResult{" +
                "message='" + message + '\'' +
                '}';
    }
}
