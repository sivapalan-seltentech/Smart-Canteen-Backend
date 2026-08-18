package com.example.smart_canteen_backend.repository;

import com.example.smart_canteen_backend.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUserId(Long userId);

    Cart findByUserIdAndFoodId(Long userId, Long foodId);

    void deleteByUserId(Long userId);
}
