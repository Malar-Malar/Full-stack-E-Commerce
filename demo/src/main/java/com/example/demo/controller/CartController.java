package com.example.demo.controller;

import com.example.demo.dto.CartItemDTO;
import com.example.demo.dto.CartItemRequest;
import com.example.demo.model.*;
import com.example.demo.service.CartService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5502"})
public class CartController {

    @Autowired
    private CartService cartService;

//    @PostMapping("/add")
//    public ResponseEntity<Object> addToCart(@RequestBody CartItem cartItem) {
//        try {
//            cartService.addProductToCart(cartItem);
//            // Return a response with a message in JSON format
//            return ResponseEntity.ok(new ResponseMessage("Product added to cart successfully!"));
//        }
////        catch (Exception e) {
////            // Return a response with an error message in JSON format
////            e.printStackTrace();
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                    .body(new ResponseMessage("Error adding product to cart"));
////        }
//
//
//        catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Invalid cart item data: " + e.getMessage()));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("Error adding product to cart"));
//        }
//    }

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

    @PostMapping("/clear/{userId}")
    public ResponseEntity<String> clearCart(@RequestBody Long userId) {
        try {
            cartService.clearCart(userId);
            return ResponseEntity.ok("Cart cleared successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to clear cart");
        }
    }

}
