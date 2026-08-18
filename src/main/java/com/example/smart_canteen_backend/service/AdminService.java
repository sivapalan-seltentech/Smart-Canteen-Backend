package com.example.smart_canteen_backend.service;

import com.example.smart_canteen_backend.entity.Employee;
import com.example.smart_canteen_backend.entity.Order;
import com.example.smart_canteen_backend.repository.UserRepository;
import com.example.smart_canteen_backend.repository.FoodRepository;
import com.example.smart_canteen_backend.repository.CategoryRepository;
import com.example.smart_canteen_backend.repository.EmployeeRepository;
import com.example.smart_canteen_backend.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.smart_canteen_backend.entity.Food;
import com.example.smart_canteen_backend.entity.Category;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private OrderRepository orderRepository;


    // ==============================
    // ADMIN DASHBOARD STATISTICS
    // ==============================

    public Map<String, Long> getDashboardStats() {

        Map<String, Long> stats = new HashMap<>();

        // User counts
        stats.put("totalUsers", userRepository.count());
        stats.put("totalStudents", userRepository.countByRole("STUDENT"));
        stats.put("totalAdmins", userRepository.countByRole("ADMIN"));

        // Other module counts
        stats.put("totalEmployees", employeeRepository.count());
        stats.put("totalFoods", foodRepository.count());
        stats.put("totalCategories", categoryRepository.count());
        stats.put("totalOrders", orderRepository.count());

        // Order status counts
        stats.put(
                "pendingOrders",
                orderRepository.countByStatus("PLACED")
        );

        stats.put(
                "completedOrders",
                orderRepository.countByStatus("COMPLETED")
        );

        stats.put(
                "cancelledOrders",
                orderRepository.countByStatus("CANCELLED")
        );

        return stats;
    }
// ==============================
// USER MANAGEMENT
// ==============================

    // Get all users
    public java.util.List<com.example.smart_canteen_backend.entity.User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public com.example.smart_canteen_backend.entity.User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Delete user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

// ==============================
// FOOD MANAGEMENT
// ==============================

    // Get all foods
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    // Get food by ID
    public Food getFoodById(Long id) {
        return foodRepository.findById(id).orElse(null);
    }

    // Add or update food
    public Food saveFood(Food food) {
        if (food.getCategory() == null || food.getCategory().getId() == null) {
            throw new IllegalArgumentException("A valid category is required.");
        }

        Category category = categoryRepository.findById(food.getCategory().getId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found."));

        food.setCategory(category);

        if (food.getRating() == null) {
            food.setRating(4.5);
        }
        if (food.getEmoji() == null || food.getEmoji().isBlank()) {
            food.setEmoji("🍽️");
        }

        return foodRepository.save(food);
    }

    // Delete food
    public void deleteFood(Long id) {
        foodRepository.deleteById(id);
    }


// ==============================
// CATEGORY MANAGEMENT
// ==============================

    // Get all categories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Get category by ID
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    // Add or update category
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Delete category
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }


// ==============================
// EMPLOYEE MANAGEMENT
// ==============================

    // Get all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Get employee by ID
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    // Add or update employee
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    // Delete employee
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

// ==============================
// ORDER MANAGEMENT
// ==============================

    // Get all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Get order by ID
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    // Update order status
    public Order updateOrderStatus(Long id, String status) {

        Order order = orderRepository.findById(id).orElse(null);

        if (order == null) {
            return null;
        }

        order.setStatus(status.toUpperCase());

        return orderRepository.save(order);
    }

    // Cancel order
    public Order cancelOrder(Long id) {

        Order order = orderRepository.findById(id).orElse(null);

        if (order == null) {
            return null;
        }

        if ("COMPLETED".equalsIgnoreCase(order.getStatus())) {
            return null;
        }

        order.setStatus("CANCELLED");

        return orderRepository.save(order);
    }

    // Assign employee to order
    public Order assignEmployee(Long orderId, Long employeeId) {

        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            return null;
        }

        Employee employee = employeeRepository.findById(employeeId).orElse(null);

        if (employee == null) {
            return null;
        }

        order.setAssignedEmployee(employee);

        return orderRepository.save(order);
    }







}

