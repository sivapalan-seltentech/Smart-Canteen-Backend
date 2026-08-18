package com.example.smart_canteen_backend.controller;

import com.example.smart_canteen_backend.entity.Order;
import com.example.smart_canteen_backend.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    @Autowired
    private OrderService orderService;


    // ==========================================
    // GET ALL ORDERS
    // ==========================================

    @GetMapping
    public List<Order> findAll() {
        return orderService.findAll();
    }


    // ==========================================
    // GET ORDER BY ID
    // ==========================================

    @GetMapping("/{id}")
    public Order findById(
            @PathVariable Long id
    ) {

        return orderService.findById(id);
    }


    // ==========================================
    // GET USER ORDERS
    // ==========================================

    @GetMapping("/user/{userId}")
    public List<Order> findByUserId(
            @PathVariable Long userId
    ) {

        return orderService.findByUserId(userId);
    }


    // ==========================================
    // PLACE ORDER
    // ==========================================

    @PostMapping("/place/{userId}")
    public Order placeOrder(
            @PathVariable Long userId
    ) {

        return orderService.placeOrder(userId);
    }


    // ==========================================
    // CANCEL ORDER
    // ==========================================

    @PutMapping("/{orderId}/cancel")
    public Order cancelOrder(
            @PathVariable Long orderId
    ) {

        return orderService.cancelOrder(orderId);
    }


    // ==========================================
    // UPDATE STATUS
    // ==========================================

    @PutMapping("/{orderId}/status")
    public Order updateStatus(
            @PathVariable Long orderId,
            @RequestParam String status
    ) {

        return orderService.updateStatus(
                orderId,
                status
        );
    }


    // ==========================================
    // ASSIGN EMPLOYEE
    // ==========================================

    @PutMapping("/{orderId}/assign/{employeeId}")
    public Order assignEmployee(
            @PathVariable Long orderId,
            @PathVariable Long employeeId
    ) {

        return orderService.assignEmployee(
                orderId,
                employeeId
        );
    }


    // ==========================================
    // EMPLOYEE ORDERS
    // ==========================================

    @GetMapping("/employee/{employeeId}")
    public List<Order> findByEmployeeId(
            @PathVariable Long employeeId
    ) {

        return orderService.findByEmployeeId(
                employeeId
        );
    }


    // ==========================================
    // EMPLOYEE PENDING ORDERS
    // ==========================================

    @GetMapping("/employee/{employeeId}/pending")
    public List<Order> findPendingOrders(
            @PathVariable Long employeeId
    ) {

        return orderService.findPendingOrders(
                employeeId
        );
    }


    // ==========================================
    // ADMIN COUNTS
    // ==========================================

    @GetMapping("/admin/pending-count")
    public long getPendingOrderCount() {

        return orderService.getPendingOrderCount();
    }


    @GetMapping("/admin/completed-count")
    public long getCompletedOrderCount() {

        return orderService.getCompletedOrderCount();
    }


    @GetMapping("/admin/cancelled-count")
    public long getCancelledOrderCount() {

        return orderService.getCancelledOrderCount();
    }
}