package com.example.orderprocessservice.service.product;

import com.example.orderprocessservice.api.dto.ProductDto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;


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
     * Checks if product of given id exists
     *
     * @param id    given id
     * @return      exits
     */
    @HystrixCommand(fallbackMethod = "getFallbackBoolean")
    public boolean existsById(String id){
        Boolean exists = restTemplate.getForObject(
                url + "existsById/" + id,
                Boolean.class
        );
        return exists.booleanValue();
    }
    public boolean getFallbackBoolean(String id){
        return true;
    }


    /**
     * Gets product by id
     *
     * @param id    given id
     * @return      product
     */
    @HystrixCommand(fallbackMethod = "getFallbackProductDto")
    public ProductDto getProductById(String id){
        return restTemplate.getForObject(
                url + "/id/" + id,
                ProductDto.class
        );
    }
    public ProductDto getFallbackProductDto(String id){
        return new ProductDto(
                id,
                "There was a problem finding product type",
                "There was a problem finding product name",
                0.0,
                0,
                "There was a problem finding product brand",
                "There was a problem finding product description"
        );
    }


    public void subtractProductAvailability(
            Map<String, Long> mapped
    ){

        for(Map.Entry<String, Long> entry : mapped.entrySet()){
            subtractProductAvailability(
                    entry.getKey(),     // product id
                    entry.getValue()    // amount
            );
        }

    }



    public void subtractProductAvailability(
            String productId,
            Long amount
    ){

        restTemplate.put(
                url +
                        "subtract?" +
                            "id=" + productId +
                            "&amount=" + amount,
                Void.class
        );

    }


}
