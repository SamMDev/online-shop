package com.example.customer_service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class ExceptionDetails {
    private Date date;
    private String message;
    private String details;
}
