package com.example.demo.controller;

import com.example.demo.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart/items")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long cartItemId) {
        boolean isDeleted = cartItemService.deleteCartItem(cartItemId);
        return isDeleted ? ResponseEntity.ok("Item deleted from cart.") : ResponseEntity.notFound().build();
    }
}
