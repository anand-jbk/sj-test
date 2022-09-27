package com.assessment.sn.mathapi.controller;

import com.assessment.sn.mathapi.Exception.UnprocessableRequestException;
import com.assessment.sn.mathapi.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class MathApiControllerAdvice {

    private static Logger LOGGER = LoggerFactory.getLogger(MathApiControllerAdvice.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse>  handleException(HttpMessageNotReadableException e){
        LOGGER.error("Exception occurred while parsing request:", e);
        return new ResponseEntity<>(new ErrorResponse("Error in parsing request",
                Collections.singletonList(e.getLocalizedMessage())),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequestException(MethodArgumentNotValidException e){
        LOGGER.error("Request Validation failed:", e);
        List<String> details = new ArrayList<>();
        e.getAllErrors().stream().forEach(objectError -> details.add(objectError.getDefaultMessage()));
        return new ResponseEntity<>(new ErrorResponse("Invalid Request", details), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnprocessableRequestException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequestException(UnprocessableRequestException e){
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), e.getDetails()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>  handleException(Exception e){
        LOGGER.error("Exception occurred:", e);
        return new ResponseEntity<>(new ErrorResponse("Internal error",
                Collections.singletonList(e.getLocalizedMessage())),HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
