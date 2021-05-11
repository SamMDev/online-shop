package com.example.searchservice.service;

import com.example.searchservice.api.dto.ProductDto;
import com.example.searchservice.enums.Sorting;
import com.example.searchservice.exceptions.NotAllowedOperationException;
import com.example.searchservice.service.product_service.ProductService;
import com.example.searchservice.string_enum_converter.SortingStringToEnumConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private final ProductService productService;
    private final ObjectMapper objectMapper;
    private final SortingStringToEnumConverter sortingStringToEnumConverter;
    @Autowired
    public SearchService(ProductService productService, ObjectMapper objectMapper, SortingStringToEnumConverter sortingStringToEnumConverter) {
        this.productService = productService;
        this.objectMapper = objectMapper;
        this.sortingStringToEnumConverter = sortingStringToEnumConverter;
    }


    /**
     * Gets products by given name
     *
     * @param name  given name
     * @return      list of products
     */
    public List<ProductDto> getByName(String name){
        return productService.getByName(name);
    }


    /**
     * Gets products by given type
     *
     * @param type  given type
     * @return      list of products
     */
    public List<ProductDto> getByType(String type){
        return productService.getByType(type);
    }


    /**
     * Gets product by brand
     *
     * @param brand given brand
     * @return      list of products
     */
    public List<ProductDto> getByBrand(String brand){
        return productService.getByBrand(brand);
    }

    /**
     * Gets all products
     *
     * @param mode  from low or high price
     * @return      list of products
     */
    public List<ProductDto> getByPrice(String mode){
        List<ProductDto> list = productService.getAll();

        Sorting sortingMode = sortingStringToEnumConverter.convert(mode);

        // from lowest to highest price
        // converts String mode into ENUM
        if (sortingMode.equals(Sorting.LOW)){
            return list
                    .stream()
                    .sorted(Comparator.comparing(ProductDto::getPrice))
                    .collect(Collectors.toList());
        }
        // from highest to lowest price
        else if (sortingMode.equals(Sorting.HIGH)){
            return list
                    .stream()
                    .sorted(Comparator.comparing(ProductDto::getPrice).reversed())
                    .collect(Collectors.toList());
        }

        // if user does not select low or high
        throw new NotAllowedOperationException(
                "Could not convert " + mode + " to sorting mode"
        );
    }



}
