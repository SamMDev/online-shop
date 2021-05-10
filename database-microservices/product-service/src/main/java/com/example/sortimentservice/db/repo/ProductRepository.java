package com.example.sortimentservice.db.repo;

import com.example.sortimentservice.db.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @Query(value = "SELECT p.id FROM Product p")
    List<String> getAllId();

    @Query(value = "SELECT p FROM Product p WHERE product_name LIKE %:name%")
    List<Product> getProductsByName(@Param("name") String name);

    @Query(value = "SELECT p FROM Product p WHERE type = :type")
    List<Product> getProductsByType(@Param("type") String type);

    @Query(value = "SELECT p FROM Product p WHERE brand = :brand")
    List<Product> getProductsByBrand(@Param("brand") String brand);


//    @Query(value = "SELECT p FROM Product p ORDER BY p.price DESC")
//    List<Product> getProductsFromMostExpensive();

}
