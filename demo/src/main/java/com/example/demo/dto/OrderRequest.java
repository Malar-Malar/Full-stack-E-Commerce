//package com.example.demo.dto;
//
//import com.example.demo.model.Product;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.Getter;
//import lombok.Setter;
//
//@Data
////@Setter
////@Getter
//@EqualsAndHashCode(exclude = "product") //  this protects from Hibernate confusion
//public class OrderRequest {
//    private Long userId;
//    private Long productId;
//    private int quantity;
//    private String name;
//    private Product product;
//    private String imageUrl;
//    private String address;
//    private String phone;
//    private String state;
//    private String pincode;
//    private String paymentMethod;
//}


package com.example.demo.dto;

import com.example.demo.model.CartItem;
import com.example.demo.model.Product;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private Long userId;

    // For Buy Now
    private Long productId;
    private Product product;
    private int quantity;
    private String imageUrl;

    // For Cart Checkout
    private List<CartItemDTO> items;

    // Common fields
    private String name;
    private String address;
    private String phone;
    private String state;
    private String pincode;
    private String paymentMethod;
}
