package com.springboot.project.ecommerce.controller;

import com.springboot.project.ecommerce.dto.PaymentDTO;
import com.springboot.project.ecommerce.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<?> processPayments(@RequestBody PaymentDTO paymentDTO) {
        return paymentService.processPayment(paymentDTO);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getPaymentStatus(@PathVariable UUID orderId) {
        return paymentService.getPaymentStatus(orderId);
    }
}
