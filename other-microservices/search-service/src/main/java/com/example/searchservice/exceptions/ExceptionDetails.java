package com.example.searchservice.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class ExceptionDetails {

    private Date date;
    private String message;
    private String details;

}
