package com.example.smart_canteen_backend.controller;

import com.example.smart_canteen_backend.entity.*;
import com.example.smart_canteen_backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Admin dashboard statistics
    @GetMapping("/dashboard")
    public Map<String, Long> getDashboardStats() {
        return adminService.getDashboardStats();
    }


// ==============================
// USER MANAGEMENT
// ==============================

// Get all users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return adminService.getAllUsers();
    }

    // Get user by ID
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        return adminService.getUserById(id);
    }

    // Delete user
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return "User deleted successfully";
    }


// ==============================
// FOOD MANAGEMENT
// ==============================

// Get all foods
    @GetMapping("/foods")
    public List<Food> getAllFoods() {
        return adminService.getAllFoods();
    }

    // Get food by ID
    @GetMapping("/foods/{id}")
    public Food getFoodById(@PathVariable Long id) {
        return adminService.getFoodById(id);
    }

    // Add food
    @PostMapping("/foods")
    public Food saveFood(@RequestBody Food food) {
        return adminService.saveFood(food);
    }

    // Delete food
    @DeleteMapping("/foods/{id}")
    public String deleteFood(@PathVariable Long id) {
        adminService.deleteFood(id);
        return "Food deleted successfully";
    }


// ==============================
// CATEGORY MANAGEMENT
// ==============================

    // Get all categories
    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return adminService.getAllCategories();
    }

    // Get category by ID
    @GetMapping("/categories/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return adminService.getCategoryById(id);
    }

    // Add category
    @PostMapping("/categories")
    public Category saveCategory(@RequestBody Category category) {
        return adminService.saveCategory(category);
    }

    // Delete category
    @DeleteMapping("/categories/{id}")
    public String deleteCategory(@PathVariable Long id) {
        adminService.deleteCategory(id);
        return "Category deleted successfully";
    }

// ==============================
// EMPLOYEE MANAGEMENT
// ==============================

// Get all employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return adminService.getAllEmployees();
    }

    // Get employee by ID
    @GetMapping("/employees/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return adminService.getEmployeeById(id);
    }

    // Add employee
    @PostMapping("/employees")
    public Employee saveEmployee(@RequestBody Employee employee) {
        return adminService.saveEmployee(employee);
    }

    // Delete employee
    @DeleteMapping("/employees/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        adminService.deleteEmployee(id);
        return "Employee deleted successfully";
    }


// ==============================
// ORDER MANAGEMENT
// ==============================

// Get all orders
    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return adminService.getAllOrders();
    }

    // Get order by ID
    @GetMapping("/orders/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return adminService.getOrderById(id);
    }

    // Update order status
    @PutMapping("/orders/{id}/status")
    public Order updateOrderStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return adminService.updateOrderStatus(id, status);
    }

    // Cancel order
    @PutMapping("/orders/{id}/cancel")
    public Order cancelOrder(@PathVariable Long id) {
        return adminService.cancelOrder(id);
    }

    // Assign employee
    @PutMapping("/orders/{orderId}/assign/{employeeId}")
    public Order assignEmployee(
            @PathVariable Long orderId,
            @PathVariable Long employeeId) {

        return adminService.assignEmployee(orderId, employeeId);
    }







}

