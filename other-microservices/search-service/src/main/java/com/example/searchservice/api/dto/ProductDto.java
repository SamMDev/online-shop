package com.example.searchservice.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String id;
    private String type;
    private String productName;
    private Double price;
    private Integer available;
    private String brand;
    private String description;
}
