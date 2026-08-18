package com.example.smart_canteen_backend.controller;

import com.example.smart_canteen_backend.entity.Cart;
import com.example.smart_canteen_backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:5173")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public List<Cart> findAll() {
        return cartService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> findById(@PathVariable Long id) {
        Cart cart = cartService.findById(id);
        return cart == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(cart);
    }

    @GetMapping("/user/{userId}")
    public List<Cart> findByUserId(@PathVariable Long userId) {
        return cartService.findByUserId(userId);
    }

    /*
     * Expected JSON:
     * {
     *   "userId": 1,
     *   "foodId": 5,
     *   "quantity": 1
     * }
     */
    @PostMapping
    public ResponseEntity<?> add(@RequestBody Map<String, Object> body) {
        try {
            Long userId = toLong(body.get("userId"));
            Long foodId = toLong(body.get("foodId"));
            Integer quantity = body.get("quantity") == null
                    ? 1
                    : toLong(body.get("quantity")).intValue();

            Cart saved = cartService.addOrIncrease(userId, foodId, quantity);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "Unable to add item to cart.",
                    "error", e.getMessage() == null ? "Unknown backend error" : e.getMessage()
            ));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body
    ) {
        try {
            Integer quantity = body.get("quantity") == null
                    ? 1
                    : toLong(body.get("quantity")).intValue();

            Cart updated = cartService.updateQuantity(id, quantity);
            return updated == null
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        cartService.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Cart item deleted successfully"));
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> clearUserCart(@PathVariable Long userId) {
        cartService.clearUserCart(userId);
        return ResponseEntity.ok(Map.of("message", "Cart cleared successfully"));
    }

    private Long toLong(Object value) {
        if (value == null) {
            throw new IllegalArgumentException("Required numeric value is missing.");
        }
        if (value instanceof Number number) {
            return number.longValue();
        }
        try {
            return Long.parseLong(value.toString());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid numeric value: " + value);
        }
    }
}
