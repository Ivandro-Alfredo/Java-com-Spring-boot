package com.mrgenius.learningjava.ToDoList.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpNotReadableException(HttpMessageNotReadableException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMostSpecificCause().getMessage());
    }
}

// e visto de forma global