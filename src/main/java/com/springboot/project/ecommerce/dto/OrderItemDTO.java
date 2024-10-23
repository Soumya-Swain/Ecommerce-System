package com.springboot.project.ecommerce.dto;

import lombok.Data;

import java.util.UUID;
@Data
public class OrderItemDTO {
    private UUID productId;
    private Long quantity;
}
