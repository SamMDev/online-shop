package com.example.customer_service.service;

import com.example.customer_service.api.dto.CustomerDto;
import com.example.customer_service.db.model.Customer;
import com.example.customer_service.db.repo.CustomerRepository;
import com.example.customer_service.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final ObjectMapper objectMapper;
    private final CustomerRepository customerRepository;
    @Autowired
    public CustomerService(ObjectMapper objectMapper, CustomerRepository customerRepository) {
        this.objectMapper = objectMapper;
        this.customerRepository = customerRepository;
    }

    /**
     * Maps customer to customerDto
     * @param customer  Customer object
     * @return          CustomerDto object
     */
    private CustomerDto mapCustomerToCustomerDto(
            Customer customer
    ){
        return objectMapper.convertValue(customer, CustomerDto.class);
    }

    /**
     * Maps List<Customer> to List<CustomerDto>
     *
     * @param customerList  Customer List
     * @return              CustomerDto List
     */
    private List<CustomerDto> mapCustomerListToCustomerDtoList(
            List<Customer> customerList
    ){
        return customerList
                .stream()
                .map(this::mapCustomerToCustomerDto)
                .collect(Collectors.toList());
    }

    /**
     * inserts customer into database
     *
     * @param customerDto   given customer
     */
    public void insertCustomer(CustomerDto customerDto){
        customerRepository.save(objectMapper.convertValue(customerDto, Customer.class));
    }

    /**
     * gets customer by id, null if not exists
     *
     * @param id    given id
     * @return      customer or null
     */
    public CustomerDto getById(
            String id
    ){
        Optional<Customer> customer = customerRepository.findById(id);

        if(customer.isEmpty())
            throw new ResourceNotFoundException(
                    "Customer with id " + id + " not found"
            );

        return mapCustomerToCustomerDto(customer.get());
    }

    /**
     * Generates unused id
     *
     * @return  unused id
     */
    public String getUnusedId(){
        String id = UUID.randomUUID().toString();
        if(customerRepository.existsById(id)) return getUnusedId();

        return id;
    }
}
