package com.example.orderservice.db.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "order_content")
    private String order;

    @Column(name = "payment")
    private Double payment;

    @Column(name = "date")
    private String date;
}
