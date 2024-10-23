package com.springboot.project.ecommerce.repository;

import com.springboot.project.ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
