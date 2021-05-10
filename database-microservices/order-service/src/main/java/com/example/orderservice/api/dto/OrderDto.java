package com.example.orderservice.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private String id;
    private String customerId;
    private String order;
    private Double payment;
    private String date;
}
