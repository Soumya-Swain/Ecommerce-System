package com.springboot.project.ecommerce.controller;

import com.springboot.project.ecommerce.dto.OrderRequestDTO;
import com.springboot.project.ecommerce.entity.Order;
import com.springboot.project.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> placeOrders(@RequestBody OrderRequestDTO orderRequestDTO){
        return orderService.placeOrder(orderRequestDTO);
    }
}
