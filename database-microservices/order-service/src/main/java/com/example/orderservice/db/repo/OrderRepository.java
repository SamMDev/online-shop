package com.example.orderservice.db.repo;

import com.example.orderservice.db.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    @Query(value = "SELECT o FROM Order o WHERE customer_id = :customerId")
    List<Order> getOrdersByCustomerId(@Param("customerId") String customerId);

    @Query(value = "SELECT o FROM Order o WHERE date = :date")
    List<Order> getOrdersByDate(@Param("date") String date);
}
