package com.example.demo.controller;

import com.example.demo.dto.CartItemDTO;
import com.example.demo.dto.CartItemRequest;
import com.example.demo.model.*;
import com.example.demo.repository.CartRepository;
import com.example.demo.service.CartService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5502","http://127.0.0.1:5501","http://localhost:3000"})
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;


    @PostMapping("/add")
    public ResponseEntity<Object> addToCart(@RequestBody CartItemRequest cartItemRequest) {
         try {
              cartService.addProductToCart(cartItemRequest);
              return ResponseEntity.ok(new ResponseMessage("Product added to cart successfully!"));
         } catch (Exception e) {
               return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                       .body(new ResponseMessage("Error adding product to cart: " + e.getMessage()));
    }
}



    @GetMapping("/{userId}")
    public List<CartItemDTO> getCartItems(@PathVariable Long userId) {
        return cartService.getCartItemsByUserId(userId); // Ensure the return type is List<CartItemDTO>
    }

//    @PostMapping("/clear/{userId}")
//    public ResponseEntity<String> clearCart(@RequestBody Long userId) {
//        try {
//            cartService.clearCart(userId);
//            return ResponseEntity.ok("Cart cleared successfully");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to clear cart");
//        }
//    }


    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<String> clearCart(@PathVariable Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            for (CartItem item : new ArrayList<>(cart.getItems())) {
                cart.removeItem(item);
            }
            cartRepository.save(cart);
            cartRepository.delete(cart);
            return ResponseEntity.ok("Cart cleared successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found");
        }
    }

}
