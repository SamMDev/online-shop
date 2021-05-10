package com.example.orderprocessservice.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String postalCode;
    private String phoneNumber;
}