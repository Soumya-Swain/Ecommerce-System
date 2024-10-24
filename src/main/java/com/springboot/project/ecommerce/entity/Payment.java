package com.springboot.project.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID paymentId;
    private UUID orderId;
    private Long customerId;
    private BigDecimal amount;
    private String paymentMethod;
    private String status;
    private LocalDateTime transactionDate;
}
