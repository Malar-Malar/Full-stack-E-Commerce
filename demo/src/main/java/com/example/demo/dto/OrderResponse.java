package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class OrderResponse {
    private String productName;
    private String productImage;
    private int quantity;
    private double totalPrice;
    private LocalDateTime orderTime;
}
