package com.example.demo.service;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Product saveProduct(Product product) {
        String catName = product.getCategory().getCategoryName();
        Category category = categoryRepository.findByCategoryName(catName)
                .orElseGet(() -> {
                    Category newCat = new Category();
                    newCat.setCategoryName(catName);
                    newCat.setImageUrl(product.getCategory().getImageUrl());
                    return categoryRepository.save(newCat);
                });

        product.setCategory(category);
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }



    public Optional<Product> getProductById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Product ID must not be null");
        }
        return productRepository.findById(id);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> searchByName(String name) {
        return productRepository.searchByName(name);
    }


}
