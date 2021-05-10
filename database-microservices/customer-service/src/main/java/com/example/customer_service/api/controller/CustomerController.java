package com.example.customer_service.api.controller;

import com.example.customer_service.api.dto.CustomerDto;
import com.example.customer_service.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    /**
     *  returns customer with given id or null
     *
     * @param id    given customer id
     * @return      customer or null
     */
    @GetMapping("/id/{id}")
    public CustomerDto getById(
            @PathVariable("id") String id
    ){
        return customerService.getById(id);
    }

    /**
     *  returns generated random unused uuid
     * 
     * @return      unused uuid
     */
    @GetMapping("/unusedId")
    public String getUnusedId(){
        return customerService.getUnusedId();
    }


    /**
     * inserts given customer
     *
     * @param customerDto   given customer
     */
    @PostMapping("/insert")
    public void insertCustomer(
            @RequestBody CustomerDto customerDto
    ){
        customerService.insertCustomer(customerDto);
    }

//    @GetMapping("/getAll")
//    public List<CustomerDto> getAll(){
//        return customerService.getAll();
//    }

}
