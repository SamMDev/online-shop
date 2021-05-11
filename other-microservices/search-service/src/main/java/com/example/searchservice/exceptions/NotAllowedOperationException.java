package com.example.searchservice.exceptions;

public class NotAllowedOperationException extends RuntimeException{
    public NotAllowedOperationException(String message){
        super(message);
    }
}
