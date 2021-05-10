package com.example.orderprocessservice.api.controller;

import com.example.orderprocessservice.api.dto.FormDto;
import com.example.orderprocessservice.fallback.FallbackHandler;
import com.example.orderprocessservice.service.OrderProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orderList")
public class OrderProcessController {

    private final OrderProcessService orderProcessService;
    @Autowired
    public OrderProcessController(OrderProcessService orderProcessService, FallbackHandler fallbackHandler) {
        this.orderProcessService = orderProcessService;
    }


    /**
     *  To confirm order of items in the basket
     *
     * @param formDto   personal info needed to create order
     */
    @PostMapping("/process")
    public void process(
            @RequestBody FormDto formDto
    ){
        orderProcessService.processForm(formDto);
    }


    /**
     *  Adds to basket item id
     *
     * @param id        Id of item being added to the basket
     */
    @PostMapping("/add/{id}")
    public void addToBasket(
            @PathVariable("id") String id
    ){
        orderProcessService.addToBasket(id);
    }
}
