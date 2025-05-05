package com.example.demo.service;

import com.example.demo.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    public boolean deleteCartItem(Long cartItemId) {
        if (cartItemRepository.existsById(cartItemId)) {
            cartItemRepository.deleteById(cartItemId);
            return true;
        } else {
            return false;
        }
    }
}
