package com.example.smart_canteen_backend.controller;

import com.example.smart_canteen_backend.entity.OrderItem;
import com.example.smart_canteen_backend.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    // Get all order items
    @GetMapping
    public List<OrderItem> findAll() {
        return orderItemService.findAll();
    }

    // Get order item by ID
    @GetMapping("/{id}")
    public OrderItem findById(@PathVariable Long id) {
        return orderItemService.findById(id);
    }

    // Save order item
    @PostMapping
    public OrderItem save(@RequestBody OrderItem orderItem) {
        return orderItemService.save(orderItem);
    }

    // Delete order item
    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable Long id) {
        orderItemService.deleteById(id);
        return "Order item deleted successfully";
    }
}
