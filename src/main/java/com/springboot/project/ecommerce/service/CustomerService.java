package com.springboot.project.ecommerce.service;

import com.springboot.project.ecommerce.entity.Customer;
import com.springboot.project.ecommerce.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer registerCustomer(Customer customer) throws Exception {
        if (customerRepository.findByname(customer.getName()).isPresent()) {
            throw new Exception("Customer already exists");
        }
        return customerRepository.save(customer);
    }

    public boolean customerExists(String email, String password) {
        if (customerRepository.findByemail(email).isPresent()) {
            Optional<Customer> existingCustomer = customerRepository.findByemail(email);
            Customer customer = existingCustomer.get();
            if(customer.getPassword().equalsIgnoreCase(password))
                return true;
            else
                return false;
        } else
            return false;
    }

    public Optional<Customer> getCustomer(Long id) {
        return customerRepository.findByid(id);
    }
}
