package com.springboot.project.ecommerce.repository;

import com.springboot.project.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<Product> findByname(String name);
}
