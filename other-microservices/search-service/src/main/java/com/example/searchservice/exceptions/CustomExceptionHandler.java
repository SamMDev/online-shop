package com.example.searchservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class CustomExceptionHandler {

    private ExceptionDetails generateExceptionDetails(
        Exception e,
        WebRequest request
    ){
        return new ExceptionDetails(
                new Date(),
                e.getMessage(),
                request.getDescription(false)
        );
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(
            Exception e,
            WebRequest request
    ){

        ExceptionDetails exceptionDetails = generateExceptionDetails(
                e,
                request
        );

        return new ResponseEntity(
                exceptionDetails,
                HttpStatus.NOT_FOUND
        );

    }


    @ExceptionHandler(NotAllowedOperationException.class)
    public ResponseEntity<?> handleNotAllowedOperationException(
            Exception e,
            WebRequest request
    ){

        ExceptionDetails exceptionDetails = generateExceptionDetails(
                e,
                request
        );

        return new ResponseEntity(
                exceptionDetails,
                HttpStatus.NOT_FOUND
        );
    }

}
