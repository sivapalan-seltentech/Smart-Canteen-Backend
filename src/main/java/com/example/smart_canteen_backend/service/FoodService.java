package com.example.smart_canteen_backend.service;

import com.example.smart_canteen_backend.entity.Category;
import com.example.smart_canteen_backend.entity.Food;
import com.example.smart_canteen_backend.repository.CategoryRepository;
import com.example.smart_canteen_backend.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Food> findAll() {
        return foodRepository.findAll();
    }

    public Food save(Food food) {
        if (food.getName() == null || food.getName().isBlank()) {
            throw new IllegalArgumentException("Food name is required.");
        }

        if (food.getPrice() == null || food.getPrice() < 0) {
            throw new IllegalArgumentException("Food price must be zero or greater.");
        }

        if (food.getCategory() == null || food.getCategory().getId() == null) {
            throw new IllegalArgumentException("A valid category is required.");
        }

        Category category = categoryRepository.findById(food.getCategory().getId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found."));

        food.setCategory(category);

        if (food.getRating() == null) {
            food.setRating(4.5);
        }

        if (food.getEmoji() == null || food.getEmoji().isBlank()) {
            food.setEmoji("🍽️");
        }

        return foodRepository.save(food);
    }

    public Food findById(Long id) {
        return foodRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        foodRepository.deleteById(id);
    }

    public List<Food> findByCategoryId(Long categoryId) {
        return foodRepository.findByCategoryId(categoryId);
    }

    public List<Food> searchByName(String name) {
        return foodRepository.findByNameContainingIgnoreCase(name);
    }
}
