package com.example.smart_canteen_backend.controller;

import com.example.smart_canteen_backend.entity.Food;
import com.example.smart_canteen_backend.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping
    public List<Food> findAll() {
        return foodService.findAll();
    }
    @GetMapping("/category/{categoryId}")
    public List<Food> findByCategoryId(@PathVariable Long categoryId) {
        return foodService.findByCategoryId(categoryId);
    }
    @GetMapping("/search")
    public List<Food> searchByName(@RequestParam String name) {
        return foodService.searchByName(name);
    }


    @GetMapping("/{id}")
    public Food findById(@PathVariable Long id) {
        return foodService.findById(id);
    }

    @PutMapping("/{id}")
    public Food update(@PathVariable Long id, @RequestBody Food food) {
        food.setId(id);
        return foodService.save(food);
    }

    @PostMapping
    public Food save(@RequestBody Food food) {
        return foodService.save(food);
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable Long id) {
        foodService.deleteById(id);
        return "Food deleted successfully";
    }
}
