package com.springboot.project.ecommerce.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;
@Data
public class PaymentDTO {
    private UUID orderId;
    private Long customerId;
    private BigDecimal amount;
    private String paymentMethod;
}
