package com.springboot.project.ecommerce.dto;

import com.springboot.project.ecommerce.entity.Customer;
import com.springboot.project.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.List;
import java.util.UUID;
@Data
public class OrderRequestDTO {
    private Long customerId;
    private List<OrderItem> items;
}
