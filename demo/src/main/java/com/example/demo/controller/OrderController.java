package com.example.demo.controller;


import com.example.demo.dto.CartItemDTO;
import com.example.demo.dto.CheckoutRequest;
import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.OrderResponse;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5502","http://127.0.0.1:5501","http://localhost:3000"})
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId){
        List<Order> orders = orderService.getOrdersByUserId(userId);
        if(orders.isEmpty()){
            return ResponseEntity.status(404).body(null);
        } else {
            return ResponseEntity.ok(orders); // productImage directly from Order entity
        }
    }



    @PostMapping("/buy")
    public ResponseEntity<Map<String, String>> buyNow(@RequestBody OrderRequest request) {
        orderService.placeOrder(request);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Order placed successfully!");

        return ResponseEntity.ok(response);
    }



    @PostMapping("/checkout/buy-now")
    public ResponseEntity<?> buyNowCheckout(@RequestBody OrderRequest request) {
        try {
            User user = userService.getCurrentUser(request.getUserId());

            // Check if it's a Cart Checkout
            if (request.getItems() != null && !request.getItems().isEmpty()) {
                for (CartItemDTO item : request.getItems()) {
                    Product product = productService.getProductById(item.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found"));

                    Order order = new Order();
                    order.setUser(user);
                    order.setProductId(product.getId());
                    order.setProductName(product.getName());
                    order.setImageUrl(product.getImage());
                    order.setQuantity(item.getQuantity());
                    order.setTotalPrice(product.getPrice() * item.getQuantity());
                    order.setOrderTime(LocalDateTime.now());
                    order.setAddress(request.getAddress() + ", " + request.getState() + ", " + request.getPincode());
                    order.setPhone(request.getPhone());
                    order.setPaymentMethod(request.getPaymentMethod());
                    order.setStatus("Placed");

                    orderRepository.save(order);
                }
            } else {
                // Buy Now
                Product product = productService.getProductById(request.getProduct().getId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));

                Order order = new Order();
                order.setUser(user);
                order.setProductId(product.getId());
                order.setProductName(product.getName());
                order.setQuantity(request.getQuantity());
                order.setTotalPrice(product.getPrice() * request.getQuantity());
                order.setOrderTime(LocalDateTime.now());
                order.setImageUrl(request.getImageUrl());
                order.setAddress(request.getAddress() + ", " + request.getState() + ", " + request.getPincode());
                order.setPhone(request.getPhone());
                order.setPaymentMethod(request.getPaymentMethod());
                order.setStatus("Placed");

                orderRepository.save(order);
            }

            return ResponseEntity.ok(Map.of("message", "Order placed successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to place order");
        }
    }








}



