package com.example.orderprocessservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;

@SpringBootApplication
@EnableCircuitBreaker
public class OrderProcessServiceApplication {

	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}

	@Bean
	public DateTimeFormatter getDateTimeFormatter(){
		return DateTimeFormatter.ofPattern("dd-MM-yyyy");
	}

	public static void main(String[] args) {
		SpringApplication.run(OrderProcessServiceApplication.class, args);
	}

}
