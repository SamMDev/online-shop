package com.example.orderservice.service;

import com.example.orderservice.api.dto.OrderDto;
import com.example.orderservice.db.repo.OrderRepository;
import com.example.orderservice.db.model.Order;
import com.example.orderservice.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, ObjectMapper objectMapper) {
        this.orderRepository = orderRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Maps Order to OrderDto
     *
     * @param order     Order object
     * @return          OrderDto object
     */
    private OrderDto mapOrderToOrderDto(
            Order order
    ){
        return objectMapper.convertValue(order, OrderDto.class);
    }

    /**
     * Maps List<Order> into List<OrderDto>
     *
     * @param list  given Order List
     * @return      OrderDto List
     */
    private List<OrderDto> mapOrderListToOrderDtoList(
            List<Order> list
    ){
        return list
                .stream()
                .map(this::mapOrderToOrderDto)
                .collect(Collectors.toList());
    }

    /**
     * Gets current date in format dd.MM.yyyy
     *
     * @return      Date as string
     */
    private String getCurrentDate(){
        return
                LocalDate.now()
                .format(
                        DateTimeFormatter.ofPattern("dd.MM.yyyy")
                );
    }

    /**
     * returns unused order id
     *
     * @return  unused id
     */
    public String getUnusedId(){
        String id = UUID.randomUUID().toString();

        // if randomly generated id is already used
        if(orderRepository.existsById(id)) return getUnusedId();
        return id;
    }

    /**
     * inserts order
     *
     * @param orderDto order to be inserted
     */
    public void insertOrder(OrderDto orderDto){
        orderRepository.save(
                objectMapper.convertValue(orderDto, Order.class)
        );
    }

    /**
     * deletes order by id if exists
     *
     * @param id
     */
    public void deleteOrderById(String id){
        orderRepository.deleteById(id);
    }



    /**
     * returns order with given id, null of not exists
     *
     * @param id    given id
     * @return      order or null
     */
    public OrderDto getOrderById(String id){
        Optional<Order> order = orderRepository.findById(id);

        if(order.isEmpty())
            throw new ResourceNotFoundException(
                    "Order with given id " + id + " not found"
            );

        return mapOrderToOrderDto(order.get());
    }


    /**
     * returns list of orders with given customer id
     *
     * @param customerId    given id
     * @return              list of orders
     */
    public List<OrderDto> getOrdersByCustomerId(String customerId){

        List<Order> ordersByCustomerId = orderRepository.getOrdersByCustomerId(customerId);

        if(ordersByCustomerId.size() == 0)
            throw new ResourceNotFoundException(
                    "Orders with customer id " + customerId + " not found"
            );

        return mapOrderListToOrderDtoList(ordersByCustomerId);
    }

    /**
     * returns list of orders with given date
     *
     * @param date          given date
     * @return              list of orders
     */
    public List<OrderDto> getOrdersByDate(String date){

        List<Order> list = orderRepository.getOrdersByDate(date);
        if(list.size() == 0)
            throw new ResourceNotFoundException(
                    "Orders with date " + date + " not found"
            );

        return mapOrderListToOrderDtoList(
                orderRepository.getOrdersByDate(date)
        );

    }

    /**
     * returns list of orders of today's date
     *
     * @return          List of orders
     */
    public List<OrderDto> getTodayOrders(){
        return getOrdersByDate(
                getCurrentDate()
        );
    }

}