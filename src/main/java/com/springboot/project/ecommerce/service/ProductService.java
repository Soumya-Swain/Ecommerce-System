package com.springboot.project.ecommerce.service;

import com.springboot.project.ecommerce.entity.Product;
import com.springboot.project.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Page<Product> getProducts(String name, String category, Pageable pageable){
        if(!name.isEmpty() && !category.isEmpty()){
            return productRepository.findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(name,category,pageable);
        }
        else if (!category.isEmpty()){
            return productRepository.findByCategoryContainingIgnoreCase(category,pageable);
        }
        else if(!name.isEmpty()){
            return productRepository.findByNameContainingIgnoreCase(name,pageable);
        }
        return productRepository.findAll(pageable);
    }

    public Optional<Product> getProductById(UUID id){
        Optional<Product> product = productRepository.findByid(id);
        return productRepository.findByid(id);
    }
}
