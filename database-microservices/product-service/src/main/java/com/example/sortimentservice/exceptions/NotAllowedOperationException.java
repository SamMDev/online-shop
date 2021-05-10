package com.example.sortimentservice.exceptions;

public class NotAllowedOperationException extends RuntimeException{
    public NotAllowedOperationException(String message){
        super(message);
    }
}
