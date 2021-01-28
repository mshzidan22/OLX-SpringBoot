package com.olx.execption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;


@ControllerAdvice(basePackages = {"com.olx"})
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(AdNotFoundException.class)
   public ResponseEntity<Object> handleAdNotFound(AdNotFoundException ex) {
        ApiError apiError = new ApiError(ex.getMessage(),HttpStatus.NOT_FOUND,ex);
        return new ResponseEntity<>(apiError,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LocationNotFoundException.class)
    public ResponseEntity<Object> handleLocationNotFound(LocationNotFoundException ex) {
        ApiError apiError = new ApiError(ex.getMessage(),HttpStatus.NOT_FOUND,ex);
        return new ResponseEntity<>(apiError,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Object> handleCategoryNotFound(CategoryNotFoundException ex) {
        ApiError apiError = new ApiError(ex.getMessage(),HttpStatus.NOT_FOUND,ex);
        return new ResponseEntity<>(apiError,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncorrectUserOrPassException.class)
    public ResponseEntity<Object> handleIncorrectUserOrPassException(IncorrectUserOrPassException ex) {
        ApiError apiError = new ApiError(ex.getMessage(),HttpStatus.UNAUTHORIZED,ex);
        return new ResponseEntity<>(apiError,HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<Object> handleDuplicateEmailException(DuplicateEmailException ex) {
        ApiError apiError = new ApiError(ex.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY,ex);
        return new ResponseEntity<>(apiError,HttpStatus.UNPROCESSABLE_ENTITY);
    }











}
