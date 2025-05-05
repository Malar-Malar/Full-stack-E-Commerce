package com.example.demo.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequest {

        private Long userId;
        private Long productId;
        private int quantity;

}
