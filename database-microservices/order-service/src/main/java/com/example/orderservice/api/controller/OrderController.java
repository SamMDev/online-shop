package com.example.orderservice.api.controller;

import com.example.orderservice.api.dto.OrderDto;
import com.example.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    /**
     * returns order with given id, null of not exists
     *
     * @param id    given id
     * @return      order or null
     */
    @GetMapping("/id/{id}")
    public OrderDto getOrderById(
            @PathVariable ("id") String id
    ){
        return orderService.getOrderById(id);
    }

    /**
     * returns unused order id
     *
     * @return  unused id
     */
    @GetMapping("/unusedId")
    public String getUnusedId(){
        return orderService.getUnusedId();
    }

    /**
     * gets today's orders
     *
     * @return      List of orders
     */
    @GetMapping("/today-orders")
    public List<OrderDto> getTodayOrders(){
        return orderService.getTodayOrders();
    }


    /**
     * deletes order by id if exists
     *
     * @param id
     */
    @DeleteMapping("/id/{id}")
    public void deleteOrderById(
            @PathVariable("id") String id
    ){
        orderService.deleteOrderById(id);
    }

    /**
     * inserts order
     *
     * @param orderDto order to be inserted
     */
    @PostMapping("/insert")
    public void insertOrder(
            @RequestBody OrderDto orderDto
    ){
        orderService.insertOrder(orderDto);
    }

}
