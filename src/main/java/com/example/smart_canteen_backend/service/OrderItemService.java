package com.example.smart_canteen_backend.service;

import com.example.smart_canteen_backend.entity.OrderItem;
import com.example.smart_canteen_backend.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    // Get all order items
    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    // Save order item
    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    // Get order item by ID
    public OrderItem findById(Long id) {
        return orderItemRepository.findById(id).orElse(null);
    }

    // Delete order item
    public void deleteById(Long id) {
        orderItemRepository.deleteById(id);
    }
}
