package com.example.searchservice.api.controller;

import com.example.searchservice.api.dto.ProductDto;
import com.example.searchservice.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;
    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    /**
     * Returns list of products of current type
     *
     * @param type  type of product
     * @return      List of products with given type
     */
    @GetMapping("/type/{type}")
    public List<ProductDto> getByType(
            @PathVariable String type
    ){
        return searchService.getByType(type);
    }

    /**
     * Returns list of products according to price
     *
     * @param price values low or high, else returns null
     *              low: from lowest to highest price
     *              high: from highest to lowest price
     * @return      list of products ordered or null
     */
    @GetMapping("/price/{price}")
    public List<ProductDto> getByPrice(
            @PathVariable String price
    ){
        return searchService.getByPrice(price);
    }

    /**
     * Gets list of products by name
     *
     * @param name  given name
     * @return      List of products
     */
    @GetMapping("/name/{name}")
    public List<ProductDto> getByName(
            @PathVariable String name
    ){
        return searchService.getByName(name);
    }

    /**
     * Gets list of products by brand
     *
     * @param brand given brand
     * @return      List of products
     */
    @GetMapping("/brand/{brand}")
    public List<ProductDto> getByBrand(
        @PathVariable String brand
    ){
        return searchService.getByBrand(brand);
    }

}
