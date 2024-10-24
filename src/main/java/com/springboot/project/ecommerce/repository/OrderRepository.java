package com.springboot.project.ecommerce.repository;

import com.springboot.project.ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    public Optional<Order> findById(UUID id);
    public List<Order> findByCustomerIdOrderByOrderDateDesc(Long customerId);
}
