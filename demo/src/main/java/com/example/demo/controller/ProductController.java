package com.example.demo.controller;

import com.example.demo.dto.ProductRequest;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.repository.ProductRepository;

import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = {"http://localhost:5500", "http://localhost:5502"})
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;




    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }


//    @DeleteMapping("/all")
//    public ResponseEntity<String> deleteAllProducts() {
//        try {
//            productService.deleteAllProducts();
//            return ResponseEntity.ok(" All products deleted successfully");
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body(" Error deleting all products: " + e.getMessage());
//        }
//    }


    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok("Product and related cart items deleted successfully");
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody ProductRequest request) {
        // Step 1: Find category by name
        Optional<Category> optionalCategory = categoryRepository.findByCategoryName(request.getCategory());

        if (optionalCategory.isEmpty()) {
            return ResponseEntity.badRequest().body("Category not found!");
        }

        Category category = optionalCategory.get();

        // Step 2: Create Product and set values
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(category); // Set the category object (foreign key)

        // Step 3: Save the product
        productRepository.save(product);

        return ResponseEntity.ok("Product added successfully!");
    }

    @GetMapping("/category/{categoryId}")
    public List<Product> getProductsByCategoryId(@PathVariable Long categoryId) {
        return productService.getProductsByCategoryId(categoryId);
    }

    @GetMapping("/search")
    public List<Product> searchByName(@RequestParam String name) {
        return productService.searchByName(name);
    }

}







