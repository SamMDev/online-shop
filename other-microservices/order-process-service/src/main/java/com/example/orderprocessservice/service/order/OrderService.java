package com.example.orderprocessservice.service.order;

import com.example.orderprocessservice.api.dto.OrderDto;
import com.example.orderprocessservice.fallback.FallbackHandler;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class OrderService {
    private final String url;
    private final RestTemplate restTemplate;
    private final FallbackHandler fallbackHandler;

    @Autowired
    public OrderService(
            @Value("${connection.url.order-service}")
                    String url,
            RestTemplate restTemplate,
            FallbackHandler fallbackHandler) {
        this.url = url;
        this.restTemplate = restTemplate;
        this.fallbackHandler = fallbackHandler;
    }

    /**
     * Inserts order into database
     *
     * @param orderDto  given order
     */
    @HystrixCommand(fallbackMethod = "fallbackInsertOrder")
    public void insertOrder(OrderDto orderDto){
        restTemplate.postForLocation(
                url + "insert/",
                orderDto
        );
    }
    public void fallbackInsertOrder(OrderDto orderDto){
        fallbackHandler.addToFallbackOrders(orderDto);
    }

    /**
     * Requests unused id for a new order
     *
     * @return  unused order
     */
    @HystrixCommand(fallbackMethod = "getFallbackId")
    public String getUnusedId(){
        return
                restTemplate.getForObject(
                        url + "unusedId",
                        String.class
                );
    }
    public String getFallbackId(){
        return UUID.randomUUID().toString();
    }



}
