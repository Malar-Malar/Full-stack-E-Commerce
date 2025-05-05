package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequest {
    private Long cartId;
    private Long productId;
    private int quantity;

    // Getters and setters
}
