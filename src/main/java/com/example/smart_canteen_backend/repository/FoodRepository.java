package com.example.smart_canteen_backend.repository;

import com.example.smart_canteen_backend.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {

    List<Food> findByCategoryId(Long categoryId);

    List<Food> findByNameContainingIgnoreCase(String name);
}