package com.example.smart_canteen_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User who placed the order
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"orders", "cart", "password"})
    private User user;

    private Double totalAmount;

    /*
        PLACED
        PREPARING
        READY
        COMPLETED
        CANCELLED
    */
    private String status;

    private String tokenNumber;

    private LocalDateTime orderDate;

    // Employee assigned to this order
    @ManyToOne
    @JoinColumn(name = "assigned_employee_id")
    @JsonIgnoreProperties({"orders", "password"})
    private Employee assignedEmployee;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("order")
    private List<OrderItem> items;

    public Order() {
    }

    public Order(
            User user,
            Double totalAmount,
            String status,
            String tokenNumber,
            LocalDateTime orderDate
    ) {
        this.user = user;
        this.totalAmount = totalAmount;
        this.status = status;
        this.tokenNumber = tokenNumber;
        this.orderDate = orderDate;
    }

    // ID

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // USER

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // TOTAL

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    // STATUS

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // TOKEN

    public String getTokenNumber() {
        return tokenNumber;
    }

    public void setTokenNumber(String tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

    // ORDER DATE

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    // EMPLOYEE

    public Employee getAssignedEmployee() {
        return assignedEmployee;
    }

    public void setAssignedEmployee(Employee assignedEmployee) {
        this.assignedEmployee = assignedEmployee;
    }

    // ITEMS

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}