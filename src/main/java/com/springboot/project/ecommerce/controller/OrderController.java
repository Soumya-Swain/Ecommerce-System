package com.springboot.project.ecommerce.controller;

import com.springboot.project.ecommerce.dto.OrderRequestDTO;
import com.springboot.project.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> placeOrders(@RequestBody OrderRequestDTO orderRequestDTO){
        return orderService.placeOrder(orderRequestDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable UUID id){
        return orderService.getOrderById(id);
    }
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getOrderByCustomerId(@PathVariable Long customerId){
        return orderService.getOrderByCustomerId(customerId);
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable UUID id, @RequestBody String status){
        return orderService.updateOrderStatus(id,status);
    }
}
