package com.springboot.project.ecommerce.service;

import com.springboot.project.ecommerce.dto.OrderRequestDTO;
import com.springboot.project.ecommerce.entity.Order;
import com.springboot.project.ecommerce.entity.OrderItem;
import com.springboot.project.ecommerce.entity.Product;
import com.springboot.project.ecommerce.repository.CustomerRepository;
import com.springboot.project.ecommerce.repository.OrderRepository;
import com.springboot.project.ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CustomerRepository customerRepository;

    public static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    @Transactional
    public ResponseEntity<?> placeOrder(OrderRequestDTO orderRequestDTO) {
        LOGGER.info("Place Order", orderRequestDTO);
        if (customerRepository.findByid(orderRequestDTO.getCustomerId()).isPresent()) {
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (OrderItem itemDTO : orderRequestDTO.getItems()) {
                Product product = productRepository.findById(itemDTO.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));
                totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity())));
            }
            Order order = new Order();
            order.setCustomerId(orderRequestDTO.getCustomerId());
            order.setOrderDate(LocalDateTime.now());
            order.setStatus("pending");
            List<OrderItem> orderItems = orderRequestDTO.getItems().stream().map(
                    itemDTO -> {
                        OrderItem orderItem = new OrderItem();
                        orderItem.setProductId(itemDTO.getProductId());
                        orderItem.setQuantity(itemDTO.getQuantity());
                        Optional<Product> product = productRepository.findById(itemDTO.getProductId());
                        BigDecimal priceAtPurchase = product.get().getPrice();
                        orderItem.setPriceAtPurchase(priceAtPurchase);
                        orderItem.setOrder(order);
                        return orderItem;
                    }).toList();
            order.setItems(orderItems);
            order.setTotalAmount(totalAmount);
            LOGGER.info("Order Placed Succcessfully", order);
            return ResponseEntity.ok(orderRepository.save(order));
        } else
            LOGGER.warn("Customer Not Found");
            return new ResponseEntity<>("Customer Not Found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getOrderById(UUID id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent())
            return ResponseEntity.ok(order);
        else
            return ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> getOrderByCustomerId(Long customerId) {
        LOGGER.info("Get Order By Customer Id", customerId);
        List<Order> order = orderRepository.findByCustomerIdOrderByOrderDateDesc(customerId);
        if (!order.isEmpty())
            return ResponseEntity.ok(orderRepository.findByCustomerIdOrderByOrderDateDesc(customerId));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Not Found");
    }

    public ResponseEntity<?> updateOrderStatus(UUID id, String status) {
        LOGGER.info("Update Order Status", id, status);
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            order.get().setStatus(status);
            return ResponseEntity.ok(orderRepository.save(order.get()));
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order Not Found");
    }
}
