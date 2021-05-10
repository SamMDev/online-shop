package com.example.orderprocessservice.service.customer;

import com.example.orderprocessservice.api.dto.CustomerDto;
import com.example.orderprocessservice.fallback.FallbackHandler;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class CustomerService {
    private final String url;
    private final RestTemplate restTemplate;
    private final FallbackHandler fallbackHandler;
    @Autowired
    public CustomerService(
            @Value("${connection.url.customer-service}") String url,
            RestTemplate restTemplate,
            FallbackHandler fallbackHandler) {
        this.url = url;
        this.restTemplate = restTemplate;
        this.fallbackHandler = fallbackHandler;
    }

    /**
     * Inserts given customer into database
     *
     * @param customerDto   given customer
     */
    @HystrixCommand(fallbackMethod = "fallbackInsertCustomer")
    public void insertCustomer(CustomerDto customerDto){
        restTemplate.postForLocation(
                url + "insert/",
                customerDto
        );
    }
    public void fallbackInsertCustomer(CustomerDto customerDto){
        fallbackHandler.addToFallbackCustomers(customerDto);
    }

    /**
     * Requests unused id for a new customer
     *
     * @return  unused id
     */
    @HystrixCommand(fallbackMethod = "getFallbackId")
    public String getUnusedId(){
        return restTemplate.getForObject(
                url + "/unusedId",
                String.class
        );
    }
    public String getFallbackId(){
        return UUID.randomUUID().toString();
    }
}
