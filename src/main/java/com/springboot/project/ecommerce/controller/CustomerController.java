package com.springboot.project.ecommerce.controller;

import com.springboot.project.ecommerce.entity.Customer;
import com.springboot.project.ecommerce.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody Customer customer) throws Exception {
        try {
            Customer newCustomer = customerService.registerCustomer(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(newCustomer);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateCustomer(@RequestBody Map<String,String> request){
        String email = request.get("email");
        String password = request.get("password");
        boolean exists = customerService.customerExists(email,password);
        if (exists)
            return ResponseEntity.ok("User Exists");
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exists");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerDetails(@PathVariable Long id){
        return customerService.getCustomer(id).map(Customer->ResponseEntity.ok(Customer))
                .orElse(ResponseEntity.status(404).build());//Alternative map(ResponseEntity::ok)
    }
}
