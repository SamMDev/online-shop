package com.example.orderprocessservice.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class FormDto {
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String postalCode;
    private String phoneNumber;
}
