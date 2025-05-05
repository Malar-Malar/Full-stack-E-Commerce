package com.example.demo.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ProductRequest {
    private String name;
    private Double price;
    private String description;
    private String imageUrl;
    private String category;
}
