package com.example.orderprocessservice.fallback;

import lombok.Getter;
import org.springframework.stereotype.Service;
import com.example.orderprocessservice.api.dto.OrderDto;
import com.example.orderprocessservice.api.dto.CustomerDto;

import java.util.ArrayList;
import java.util.List;

@Service
@Getter
public class FallbackHandler {
    /**
     * If there is a problem with some ms,
     * fallbackHandler will hold the information till
     * ms work again
     */
    private List<OrderDto> fallbackOrders = new ArrayList<>();
    private List<CustomerDto> fallbackCustomers = new ArrayList<>();


    public void addToFallbackOrders(OrderDto orderDto){
        fallbackOrders.add(orderDto);
    }

    public void addToFallbackCustomers(CustomerDto customerDto){
        fallbackCustomers.add(customerDto);
    }
}
