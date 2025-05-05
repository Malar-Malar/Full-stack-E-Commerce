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

//    public void addProductToCart(CartItem cartItem) {
//        Long userId = cartItem.getCart().getUserId();
//
//        Cart cart = cartRepository.findByUserId(userId);
//
//        if (cart == null) {
//            cart = new Cart();
//            cart.setUserId(userId);
//            cart = cartRepository.save(cart);
//        }
//
//        Optional<CartItem> existingItem = cartItemRepository.findByCartAndProduct(cart, cartItem.getProduct());
//
//        if (existingItem.isPresent()) {
//            CartItem item = existingItem.get();
//            item.setQuantity(item.getQuantity() + cartItem.getQuantity());
//            cartItemRepository.save(item);
//        } else {
//            cartItem.setCart(cart);
//            cartItemRepository.save(cartItem);
//        }
//    }


//    public void addProductToCart(CartItem cartItem) {
//        Long userId = cartItem.getUser().getId(); // Get userId from the user associated with the cartItem
//
//        // Fetch or create the cart associated with the user
//        Cart cart = cartRepository.findByUserId(userId);
//
//        if (cart == null) {
//            cart = new Cart();
//            cart.setUserId(userId);  // Set the userId for the new cart
//            cart = cartRepository.save(cart);  // Save the new cart
//        }
//
//        // Associate the Cart with the CartItem
//        cartItem.setCart(cart);
//
//        // Check if this CartItem already exists in the cart
//        Optional<CartItem> existingItem = cartItemRepository.findByCartAndProduct(cart, cartItem.getProduct());
//
//        if (existingItem.isPresent()) {
//            CartItem item = existingItem.get();
//            item.setQuantity(item.getQuantity() + cartItem.getQuantity());
//            cartItemRepository.save(item); // Update the existing item
//        } else {
//            cartItemRepository.save(cartItem); // Save the new cart item
//        }
//    }

//    public void addProductToCart(CartItem cartItem) {
//        if (cartItem == null || cartItem.getProduct() == null || cartItem.getCart() == null) {
//            throw new IllegalArgumentException("Invalid cart item data");
//        }
//
//        Long userId = cartItem.getUser().getId(); // Check if cartItem.getUser() is null
//
//        Cart cart = cartRepository.findByUserId(userId);
//        if (cart == null) {
//            cart = new Cart();
//            cart.setUserId(userId); // This sets the user to the new cart
//            cart = cartRepository.save(cart);
//        }
//
//        Optional<CartItem> existingItem = cartItemRepository.findByCartAndProduct(cart, cartItem.getProduct());
//        if (existingItem.isPresent()) {
//            CartItem item = existingItem.get();
//            item.setQuantity(item.getQuantity() + cartItem.getQuantity());
//            cartItemRepository.save(item);
//        } else {
//            cartItem.setCart(cart);
//            cartItemRepository.save(cartItem);
//        }
//    }





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





