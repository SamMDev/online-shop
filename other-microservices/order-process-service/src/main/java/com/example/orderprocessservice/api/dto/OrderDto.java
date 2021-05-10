package com.example.orderprocessservice.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private String id;
    // private String productId;
    private String customerId;
    // private Integer amount;
    private String order;
    private Double payment;
    private String date;
}
