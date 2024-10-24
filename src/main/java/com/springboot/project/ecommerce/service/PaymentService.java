package com.springboot.project.ecommerce.service;

import com.springboot.project.ecommerce.dto.PaymentDTO;
import com.springboot.project.ecommerce.entity.Payment;
import com.springboot.project.ecommerce.repository.CustomerRepository;
import com.springboot.project.ecommerce.repository.OrderRepository;
import com.springboot.project.ecommerce.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;

    public ResponseEntity<?> processPayment(PaymentDTO paymentDTO){
        if (customerRepository.findByid(paymentDTO.getCustomerId()).isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Not found");
        else if (orderRepository.findById(paymentDTO.getOrderId()).isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order Not found");
        else{
            Payment payment = new Payment();
            payment.setCustomerId(paymentDTO.getCustomerId());
            payment.setOrderId(paymentDTO.getOrderId());
            payment.setAmount(paymentDTO.getAmount());
            payment.setPaymentMethod(paymentDTO.getPaymentMethod());
            payment.setStatus("SUCCESS");
            payment.setTransactionDate(LocalDateTime.now());
            return ResponseEntity.ok(paymentRepository.save(payment));
        }
    }

    public ResponseEntity<?> getPaymentStatus(UUID orderId){
       Optional<Payment> payment = paymentRepository.findByOrderId(orderId);
       if (payment.isPresent()) {
           String status = payment.get().getStatus();
           return ResponseEntity.ok(status);
       }
       else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment record not found");
       }
    }

}
