package com.example.searchservice.service.product_service;

import com.example.searchservice.api.dto.ProductDto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final String url;
    private final RestTemplate restTemplate;

    @Autowired
    public ProductService(
            @Value("${connection.url.product-service}")
            String url,
            RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    /**
     * Gets products by given name
     *
     * @param name  given name
     * @return      list of products
     */
    @HystrixCommand(fallbackMethod = "getFallbackList")
    public List<ProductDto> getByName(String name){


        // requests array of products and creates list
        List<ProductDto> list =
                Arrays.stream(
                    Objects.requireNonNull(restTemplate.getForObject(
                            url + "name/" + name,
                            ProductDto[].class
                    ))
                ).collect(Collectors.toList());

        // throws exception if list is empty
        if(list.size() == 0)
            throw new ResourceAccessException(
                    "Products with given name " + name + " not found"
            );

        return list;
    }

    /**
     * Gets products by given type
     *
     * @param type  given type
     * @return      list of products
     */
    @HystrixCommand(fallbackMethod = "getFallbackList")
    public List<ProductDto> getByType(String type){
        // requests array of products and creates list
        List<ProductDto> list =
            Arrays.stream(
                Objects.requireNonNull(restTemplate.getForObject(
                        url + "type/" + type,
                        ProductDto[].class
                ))
            ).collect(Collectors.toList());

        // throws exception if list is empty
        if(list.size() == 0)
            throw new ResourceAccessException(
                    "Products with given type " + type + " not found"
            );

        return list;
    }


    /**
     * Gets products by given brand
     *
     * @param brand given brand
     * @return      list of products
     */
    @HystrixCommand(fallbackMethod = "getFallbackList")
    public List<ProductDto> getByBrand(
            String brand
    ){
        List<ProductDto> list =
                Arrays.stream(
                    Objects.requireNonNull(restTemplate.getForObject(
                            url + "brand/" + brand,
                            ProductDto[].class
                    ))
                ).collect(Collectors.toList());

        if(list.size() == 0)
            throw new ResourceAccessException(
                    "Products with given brand " + brand + " not found"
            );

        return list;

    }


    /**
     * Gets all the products
     *
     * @return  list of products
     */
    @HystrixCommand(fallbackMethod = "getFallbackList")
    public List<ProductDto> getAll(){
        return Arrays.stream(
                Objects.requireNonNull(restTemplate.getForObject(
                        url + "all/",
                        ProductDto[].class
                ))
        ).collect(Collectors.toList());
    }


    /*
    FALLBACK METHODS
     */

    /**
     * @return Empty list
     */
    public List<ProductDto> getFallbackList(){
        return Collections.emptyList();
    }
    public List<ProductDto> getFallbackList(String sth){
        return Collections.emptyList();
    }
}
