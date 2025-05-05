package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutRequest {
    private Long userId;
    private String name;
    private String phone;
    private String address;
    private String state;
    private String pincode;
    private int quantity;

}
