package com.example.smart_canteen_backend.service;

import com.example.smart_canteen_backend.entity.Cart;
import com.example.smart_canteen_backend.entity.Food;
import com.example.smart_canteen_backend.entity.User;
import com.example.smart_canteen_backend.repository.CartRepository;
import com.example.smart_canteen_backend.repository.FoodRepository;
import com.example.smart_canteen_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FoodRepository foodRepository;

    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    public Cart findById(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    public List<Cart> findByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Transactional
    public Cart addOrIncrease(Long userId, Long foodId, int quantity) {
        if (userId == null || foodId == null) {
            throw new IllegalArgumentException("User ID and food ID are required.");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be at least 1.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new IllegalArgumentException("Food not found."));

        Cart existing = cartRepository.findByUserIdAndFoodId(userId, foodId);

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
            return cartRepository.save(existing);
        }

        return cartRepository.save(new Cart(user, food, quantity));
    }

    @Transactional
    public Cart updateQuantity(Long cartId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found."));

        if (quantity <= 0) {
            cartRepository.delete(cart);
            return null;
        }

        cart.setQuantity(quantity);
        return cartRepository.save(cart);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!cartRepository.existsById(id)) {
            return;
        }
        cartRepository.deleteById(id);
    }

    @Transactional
    public void clearUserCart(Long userId) {
        cartRepository.deleteByUserId(userId);
    }

    public double calculateTotal(Long userId) {
        return cartRepository.findByUserId(userId)
                .stream()
                .filter(cart -> cart.getFood() != null)
                .mapToDouble(cart -> cart.getFood().getPrice() * cart.getQuantity())
                .sum();
    }
}
