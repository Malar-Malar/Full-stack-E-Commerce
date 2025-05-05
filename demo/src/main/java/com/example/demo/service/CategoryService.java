package com.example.demo.service;

import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByCategoryName(name);
    }
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

}
