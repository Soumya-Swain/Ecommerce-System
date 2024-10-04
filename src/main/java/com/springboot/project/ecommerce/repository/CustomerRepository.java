package com.springboot.project.ecommerce.repository;

import com.springboot.project.ecommerce.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Optional<Customer> findByname(String name);
    Optional<Customer> findByemail(String email);
    Optional<Customer> findByid(Long id);

}
