package com.example.demo.service;


import com.example.demo.dto.CheckoutRequest;
import com.example.demo.dto.OrderRequest;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    public List<Order> getOrdersByUserId(Long userId){
        return orderRepository.findByUserId(userId);
    }


//    public void placeOrder(OrderRequest request) {
//        try {
//            User user = userRepository.findById(request.getUserId())
//                    .orElseThrow(() -> new RuntimeException("User not found"));
//
//            // Check if the request is from Cart (product == null) or Buy Now
//            if (request.getProduct() == null && request.getProductId() == null) {
//                // From Cart
//                Cart cart = cartRepository.findByUserId(request.getUserId());
//                List<CartItem> cartItems = cart.getItems();
//                for (CartItem item : cartItems) {
//                    Product product = item.getProduct();
//
//                    Order order = new Order();
//                    order.setUser(user);
//                    order.setProductId(product.getId());
//                    order.setProductName(product.getName());
//                    order.setQuantity(item.getQuantity());
//                    order.setTotalPrice(product.getPrice() * item.getQuantity());
//                    order.setImageUrl(product.getImageUrl());
//                    order.setOrderTime(LocalDateTime.now());
//                    order.setStatus("Placed");
//
//                    order.setAddress(request.getAddress() + ", " + request.getState() + ", " + request.getPincode());
//                    order.setPhone(request.getPhone());
//                    order.setPaymentMethod(request.getPaymentMethod());
//
//                    orderRepository.save(order);
//
//                    cartItemRepository.deleteAll(cartItems);
//                    cartRepository.delete(cart);
//                }
//
//
//
//            } else {
//                // Buy Now logic
//                Product product = productRepository.findById(request.getProduct().getId())
//                        .orElseThrow(() -> new RuntimeException("Product not found"));
//
//                Order order = new Order();
//                order.setUser(user);
//                order.setProductId(product.getId());
//                order.setProductName(product.getName());
//                order.setQuantity(request.getQuantity());
//                order.setTotalPrice(product.getPrice() * request.getQuantity());
//                order.setImageUrl(request.getImageUrl());
//                order.setOrderTime(LocalDateTime.now());
//                order.setStatus("Placed");
//
//                order.setAddress(request.getAddress() + ", " + request.getState() + ", " + request.getPincode());
//                order.setPhone(request.getPhone());
//                order.setPaymentMethod(request.getPaymentMethod());
//
//                orderRepository.save(order);
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("Order placing failed: " + e.getMessage());
//        }
//    }


    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Transactional
    public void placeOrder(OrderRequest request) {
        try {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (request.getProduct() == null && request.getProductId() == null) {
                // Cart checkout
                Cart cart = cartRepository.findByUserId(request.getUserId());
                List<CartItem> cartItems = cart.getItems();
                System.out.println("Cart Items: " + cartItems.size());

                for (CartItem item : cartItems) {
                    Product product = item.getProduct();

                    Order order = new Order();
                    order.setUser(user);
                    order.setProductId(product.getId());
                    order.setProductName(product.getName());
                    order.setQuantity(item.getQuantity());
                    order.setTotalPrice(product.getPrice() * item.getQuantity());
                    order.setImageUrl(product.getImageUrl());
                    order.setOrderTime(LocalDateTime.now());
                    order.setStatus("Placed");

                    order.setAddress(request.getAddress() + ", " + request.getState() + ", " + request.getPincode());
                    order.setPhone(request.getPhone());
                    order.setPaymentMethod(request.getPaymentMethod());

                    orderRepository.save(order);
                }
            } else {
                // Buy Now
                Product product = productRepository.findById(request.getProduct().getId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));

                Order order = new Order();
                order.setUser(user);
                order.setProductId(product.getId());
                order.setProductName(product.getName());
                order.setQuantity(request.getQuantity());
                order.setTotalPrice(product.getPrice() * request.getQuantity());
                order.setImageUrl(request.getImageUrl());
                order.setOrderTime(LocalDateTime.now());
                order.setStatus("Placed");

                order.setAddress(request.getAddress() + ", " + request.getState() + ", " + request.getPincode());
                order.setPhone(request.getPhone());
                order.setPaymentMethod(request.getPaymentMethod());

                orderRepository.save(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Order placing failed: " + e.getMessage());
        }
    }







}
