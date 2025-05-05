package com.example.demo.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private String productName;
    private int quantity;
    private double totalPrice;

    @Column(name = "image_url")
    private String imageUrl;



    private String address;
    private String phone;
    private String status;

    @Column
    private String paymentMethod;


    @ManyToOne
   @JoinColumn(name="user_id")
   private User user;

   private LocalDateTime orderTime;

}
