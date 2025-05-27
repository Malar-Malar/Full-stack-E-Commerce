package com.example.demo.service;

import com.example.demo.dto.CartItemRequest;
import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.dto.CartItemDTO;
import com.example.demo.model.Product;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductRepository productRepository;

    private Cart getCartFromSession() {
        // Implement the logic to retrieve the current user's cart
        return new Cart();
    }

    @Transactional
    public List<CartItemDTO> getCartItemsByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            List<CartItemDTO> dtoList = new ArrayList<>();
            for (CartItem cartItem : cartItemRepository.findByCart(cart)) {
                dtoList.add(new CartItemDTO(cartItem)); // Convert CartItem to CartItemDTO
            }
            return dtoList;
        }
        return new ArrayList<>();
    }

    @Transactional
    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            cartItemRepository.deleteByCart(cart);
        }
    }

    public CartItemDTO addProductToCart(CartItemRequest request) {
        Long userId = request.getUserId();
        Long productId = request.getProductId();
        int quantity = request.getQuantity();

        // Fetch the cart for the user (or create a new one)
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cart = cartRepository.save(cart);
        }

        // Fetch the product using the productId
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        // Find if the CartItem already exists for the user and product
        Optional<CartItem> existingItem = cartItemRepository.findByCartAndProduct(cart, product);
        CartItem cartItem;
        if (existingItem.isPresent()) {
            cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity); // Update the quantity
        } else {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product); // Set the product properly
            cartItem.setQuantity(quantity);
        }

        // Save the CartItem (either updated or newly created)
        cartItem = cartItemRepository.save(cartItem);

        // Convert the CartItem entity to CartItemDTO and return
        return new CartItemDTO(cartItem);
    }





}





