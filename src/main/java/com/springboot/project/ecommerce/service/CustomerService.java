package com.springboot.project.ecommerce.service;

import com.springboot.project.ecommerce.entity.Customer;
import com.springboot.project.ecommerce.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    private static final Logger Logger = LoggerFactory.getLogger(CustomerService.class);

    public Customer registerCustomer(Customer customer) throws Exception {
        Logger.info("Registering Customer",customer);
        if (customerRepository.findByname(customer.getName()).isPresent()) {
            Logger.warn("Customer Already Exists");
            throw new Exception("Customer already exists");
        } else {
            if (this.isPatternMatching(customer, "phoneNumber")) {
                Logger.warn("Invalid Phone Number");
                throw new Exception("Invalid Phone Number");
            }
            if (this.isPatternMatching(customer, "email")) {
                Logger.warn("Invalid Email");
                throw new Exception("Invalid Email");
            }
            if (this.isPatternMatching(customer, "address")) {
                Logger.warn("Invalid Address");
                throw new Exception("Invalid Address");
            }
            Logger.info("Customer Registered Succcessfully");
            return customerRepository.save(customer);
        }

    }

    public boolean customerExists(String email, String password) {
        Logger.info("Customer Exists", email);
        if (customerRepository.findByemail(email).isPresent()) {
            Optional<Customer> existingCustomer = customerRepository.findByemail(email);
            Customer customer = existingCustomer.get();
            if (customer.getPassword().equalsIgnoreCase(password))
                return true;
            else
                return false;
        } else
            return false;
    }

    public Optional<Customer> getCustomer(Long id) {
        Logger.info("Get Customer", id);
        return customerRepository.findByid(id);
    }

    public boolean isPatternMatching(Customer customer, String field) {
        Pattern pattern;
        Matcher matcher = null;
        boolean valid = false;

        String phoneNumberRegex = "^(?!.*(\\d)\\1{9})(\\d){10}$";
        String emailRegex = "^[\\w\\d.+-]+@[\\w\\d.-]+\\.[\\w\\d]{2,3}$";
        String addressRegex = "^.{5,}$";

        if (field.equalsIgnoreCase("phoneNumber")) {
            pattern = Pattern.compile(phoneNumberRegex);
            matcher = pattern.matcher(customer.getPhoneNumber());
        } else if (field.equalsIgnoreCase("email")) {
            pattern = Pattern.compile(emailRegex);
            matcher = pattern.matcher(customer.getEmail());
        } else if (field.equalsIgnoreCase("address")) {
            pattern = Pattern.compile(addressRegex);
            matcher = pattern.matcher(customer.getAddress());
        }
        if (matcher != null)
            valid = matcher.matches();
        return !valid;
    }
}
