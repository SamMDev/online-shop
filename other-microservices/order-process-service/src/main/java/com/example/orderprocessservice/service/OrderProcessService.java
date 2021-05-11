package com.example.orderprocessservice.service;

import com.example.orderprocessservice.api.dto.CustomerDto;
import com.example.orderprocessservice.api.dto.FormDto;
import com.example.orderprocessservice.api.dto.OrderDto;
import com.example.orderprocessservice.api.dto.ProductDto;
import com.example.orderprocessservice.exceptions.ResourceNotFoundException;
import com.example.orderprocessservice.service.customer.CustomerService;
import com.example.orderprocessservice.service.order.OrderService;
import com.example.orderprocessservice.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderProcessService {
    // Ids of products customer added to the basket
    private List<String> productsInBasket = new ArrayList<>();

    // to format date
    private final DateTimeFormatter formatter;

    private final ProductService productService;
    private final CustomerService customerService;
    private final OrderService orderService;
    @Autowired
    public OrderProcessService(DateTimeFormatter formatter, ProductService productService, CustomerService customerService, OrderService orderService) {
        this.formatter = formatter;
        this.productService = productService;
        this.customerService = customerService;
        this.orderService = orderService;
    }

    /**
     * Calculates the payment from order map
     *
     * @param map   Map of ordered items
     * @return      calculated payment
     */
    private Double calculatePayment(Map<String, Long> map){
        Double payment = 0.0;

        ProductDto productDto;
        for(Map.Entry<String, Long> entry : map.entrySet()){
            /**
             * gets product by id
             * only existing ids are in map
             */
            productDto = productService.getProductById(entry.getKey());

            payment +=
                    productDto.getPrice()       // price of the product
                            * entry.getValue(); // amount of a product
        }

        return payment;
    }

    /**
     * Maps order list
     *
     * @return  Mapped order
     */
    private Map<String, Long> getMappedOrder(){
        return
                productsInBasket.stream()
                        .collect(Collectors.groupingBy(
                                // for each element
                                (element -> element),
                                // counts its occurrence in list
                                Collectors.counting()
                        ));
    }

    /**
     *  adds product id to basket
     *  only existing ids
     *
     * @param id    product id
     */
    public void addToBasket(String id){

        if(!productService.existsById(id))
            throw new ResourceNotFoundException(
                    "Product with given id " + id + " can not be added to basket\n" +
                            "No product with such id"
            );

        productsInBasket.add(id);
    }

    /**
     * Gets current date
     *
     * @return  current date
     */
    private String getDate(){
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(formatter);
    }

    /**
     * Executes the order
     *
     * @param formDto   given form with customer personal data
     */
    public void processForm(
            FormDto formDto
    ){
        // basket must not be empty
        if(productsInBasket.size() > 0) {
            // gets unused id for the order
            String orderId = orderService.getUnusedId();
            // gets unused id for the customer
            String customerId = customerService.getUnusedId();

            String date = getDate();
            // gets order list mapped
            Map<String, Long> map = getMappedOrder();
            // calculates the whole payment
            Double payment = calculatePayment(map);

            // inserts customer into database
            customerService.insertCustomer(
                    new CustomerDto(
                            customerId,
                            formDto.getFirstName(),
                            formDto.getLastName(),
                            formDto.getEmail(),
                            formDto.getAddress(),
                            formDto.getPostalCode(),
                            formDto.getPhoneNumber()
                    )
            );

            // inserts order into database
            orderService.insertOrder(
                    new OrderDto(
                            orderId,
                            customerId,
                            map.toString(),
                            payment,
                            date
                    )
            );

            // subtracts availability number in database
            productService.subtractProductAvailability(map);


            // empty the basket
            productsInBasket.clear();
        }
    }
}
