package com.example.sortimentservice.api.controller;

import com.example.sortimentservice.api.dto.ProductDto;
import com.example.sortimentservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Gets all products
     *
     * @return  list of products
     */
    @GetMapping("/all")
    public List<ProductDto> getAllProducts(){
        return productService.getAllProducts();
    }

    /**
     * gets product by given id, null if not exists
     *
     * @param id    given id
     * @return      product or null
     */
    @GetMapping("/id/{id}")
    public ProductDto getProductById(
            @PathVariable("id") String id
    ){
        return productService.getProductById(id);
    }

    /**
     * gets all ids of products
     *
     * @return      list of product ids
     */
    @GetMapping("/all/id")
    public List<String> getAllId(){
        return productService.getAllId();
    }

    /**
     * get products by name
     *
     * @param name  given name
     * @return      list of products
     */
    @GetMapping("/name/{name}")
    public List<ProductDto> getProductsByName(
            @PathVariable("name") String name
    ){
        return productService.getProductsByName(name);
    }

    /**
     * gets products by type
     *
     * @param type  given type
     * @return      list of products
     */
    @GetMapping("/type/{type}")
    public List<ProductDto> getProductsByType(
            @PathVariable("type") String type
    ){
        return productService.getProductsByType(type);
    }


    /**
     * gets products by brand
     *
     * @param brand     given brand
     * @return          list of products
     */
    @GetMapping("/brand/{brand}")
    public List<ProductDto> getProductsByBrand(
            @PathVariable("brand") String brand
    ){
        return productService.getProductsByBrand(brand);
    }

    /**
     * if exists record with given id
     *
     * @param id    given id
     * @return      true/false
     */
    @GetMapping("/existsById/{id}")
    public boolean existsById(
            @PathVariable("id") String id){
        System.out.println(id);
        return productService.existsById(id);
    }


    /**
     * Subtracts from available pieces of product with given id
     *
     * @param id        product id
     * @param amount    amount to subtract
     */
    @PutMapping("/subtract")
    public void subtractFromAvailablePieces(
            @RequestParam("id")     String id,
            @RequestParam("amount") Integer amount
    ){
        productService.subtractFromAvailablePieces(id, amount);
    }


}
