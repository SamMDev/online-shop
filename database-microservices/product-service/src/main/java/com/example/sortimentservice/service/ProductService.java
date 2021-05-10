package com.example.sortimentservice.service;

import com.example.sortimentservice.api.dto.ProductDto;
import com.example.sortimentservice.db.model.Product;
import com.example.sortimentservice.db.repo.ProductRepository;
import com.example.sortimentservice.exceptions.NotAllowedOperationException;
import com.example.sortimentservice.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;
    @Autowired
    public ProductService(ProductRepository productRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }


    /**
     * Converts Product List into ProductDto List
     *
     * @param list  Product List
     * @return      ProductDto List
     */
    public List<ProductDto> convertFromProductEntityListToProductDtoList(
            List<Product> list
    ){
        return list
                .stream()
                .map(product -> objectMapper.convertValue(product, ProductDto.class))
                .collect(Collectors.toList());
    }


    /**
     * Gets all products
     *
     * @return  list of products
     */
    public List<ProductDto> getAllProducts(){
        return convertFromProductEntityListToProductDtoList(
                productRepository.findAll()
        );
    }

    /**
     * gets product by given id, null of not exists
     *
     * @param productId     given id
     * @return              product or null
     */
    public ProductDto getProductById(String productId)
            throws ResourceNotFoundException {

        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty())
            throw new ResourceNotFoundException(
                    "Product with " + productId + " ID not found"
            );

        return objectMapper.convertValue(product.get(), ProductDto.class);
    }

    /**
     * inserts product
     *
     * @param productDto    given product
     */
    public void insertProduct(
            ProductDto productDto
    ){
        productRepository.save(
                // converts ProductDto into Product class
                objectMapper.convertValue(productDto, Product.class));
    }

    /**
     * gets all ids of products
     *
     * @return      list of product ids
     */
    public List<String> getAllId(){
        return productRepository.getAllId();
    }


    /**
     * get products by name
     *
     * @param name  given name
     * @return      list of products
     */
    public List<ProductDto> getProductsByName(
            String name
    ){
        return convertFromProductEntityListToProductDtoList(
                productRepository.getProductsByName(name)
        );
    }


    /**
     * gets products by type
     *
     * @param type  given type
     * @return      list of products
     */
    public List<ProductDto> getProductsByType(
            String type
    ){
        return
                convertFromProductEntityListToProductDtoList(
                        productRepository.getProductsByType(type)
                );
    }


    /**
     * gets products by brand
     *
     * @param brand     given brand
     * @return          list of products
     */
    public List<ProductDto> getProductsByBrand(
            String brand
    ){
        return
                convertFromProductEntityListToProductDtoList(
                        productRepository.getProductsByBrand(brand)
                );
    }

    /**
     * if exists record with given id
     *
     * @param id    given id
     * @return      true/false
     */
    public boolean existsById(String id){
        return productRepository.existsById(id);
    }




    /**
     * Subtracts from available pieces in database
     *
     * @param id        id of product to be subtracted from
     * @param amount    number to subtract from product's amount in db
     */
    public void subtractFromAvailablePieces(
            String id,
            Integer amount
    ) throws ResourceNotFoundException {

        Optional<Product> product = productRepository.findById(id);

        if(product.isEmpty()) throw new ResourceNotFoundException(
                "Product with " + id + " ID not found"
        );

        // get number of available pieces of product
        Integer availableAfterSubtraction = product.get().getAvailable() - amount;

        // available can not be negative number
        if(availableAfterSubtraction < 0)
            throw new NotAllowedOperationException(
                    "Amount > available products"
            );

        // sets available to number after substraction
        product.get().setAvailable(availableAfterSubtraction);
        // update data in database
        productRepository.save(product.get());
    }
}
