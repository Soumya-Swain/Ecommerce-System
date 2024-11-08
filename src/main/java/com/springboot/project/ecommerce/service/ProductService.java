package com.springboot.project.ecommerce.service;

import com.springboot.project.ecommerce.entity.Product;
import com.springboot.project.ecommerce.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    public Product createProduct(Product product) {
        LOGGER.info("Create Product", product);
        return productRepository.save(product);
    }

    public Page<Product> getProducts(String name, String category, Pageable pageable){
        LOGGER.info("Get Products",name,category,pageable);
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
        LOGGER.info("Get Product",id);
        Optional<Product> product = productRepository.findByid(id);
        return productRepository.findByid(id);
    }

    public Optional<Product> updateProduct(UUID id, Product updatedProduct) throws Exception {
        LOGGER.info("Update Product",id,updatedProduct);
        return productRepository.findByid(id).map(existingProduct ->{
           existingProduct.setName(updatedProduct.getName());
           existingProduct.setDescription(updatedProduct.getDescription());
           existingProduct.setCategory(updatedProduct.getCategory());
           existingProduct.setPrice(updatedProduct.getPrice());
           existingProduct.setStockQuantity(updatedProduct.getStockQuantity());
           existingProduct.setUpdatedAt(LocalDateTime.now());
            LOGGER.info("Product Updated",existingProduct);
           return productRepository.save(existingProduct);
        });
    }

    public ResponseEntity<?> deleteProduct(UUID id){
        LOGGER.info("Delete Product",id);
        productRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted the ID: "+id);
    }
}
