package com.springbootapi.demo.handler;

import com.springbootapi.demo.Exception.InvalidBookException;
import com.springbootapi.demo.Exception.NotFoundException;
import com.springbootapi.demo.resource.ErrorResult;
import com.springbootapi.demo.resource.FieldResource;
import com.springbootapi.demo.resource.InvalidErrorResouce;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    /**
     * handle cannot found resource error
     * @param e
     * @return
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<?> handleNotFound(RuntimeException e) {
        ErrorResult errorResult = new ErrorResult(e.getMessage());
        ResponseEntity result = new ResponseEntity<Object>(errorResult, HttpStatus.NOT_FOUND);
        log.error("Error ------ {} ", result);
        return result;
    }

    /**
     * handle parameter check fail exception
     * @param e
     * @return
     */
    @ExceptionHandler(InvalidBookException.class)
    @ResponseBody
    public ResponseEntity<?> handleInvalidRequest(InvalidBookException e) {
        Errors errors = e.getErrors();
        List<FieldError> fieldErrors = errors.getFieldErrors();
        List<FieldResource> fieldResources = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            FieldResource fieldResource = new FieldResource(fieldError.getObjectName(),
                                                            fieldError.getField(),
                                                            fieldError.getCode(),
                                                            fieldError.getDefaultMessage());
            fieldResources.add(fieldResource);
        }
        InvalidErrorResouce invalidErrorResouce = new InvalidErrorResouce(e.getMessage(), fieldResources);
        ResponseEntity result = new ResponseEntity<Object>(invalidErrorResouce, HttpStatus.BAD_REQUEST);
        log.error("Error ------ {} ", result);
        return result;
    }

    /**
     * deal rest of other exceptions
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> handleException(Exception e) {
        log.error("Error ------ {} ", e.getMessage());
        return new ResponseEntity<Object>("catch other exception ", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

