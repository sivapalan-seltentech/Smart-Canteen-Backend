package com.example.smart_canteen_backend.service;

import com.example.smart_canteen_backend.entity.Cart;
import com.example.smart_canteen_backend.entity.Employee;
import com.example.smart_canteen_backend.entity.Order;
import com.example.smart_canteen_backend.entity.OrderItem;
import com.example.smart_canteen_backend.entity.User;

import com.example.smart_canteen_backend.repository.CartRepository;
import com.example.smart_canteen_backend.repository.EmployeeRepository;
import com.example.smart_canteen_backend.repository.OrderItemRepository;
import com.example.smart_canteen_backend.repository.OrderRepository;
import com.example.smart_canteen_backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    // ==========================================
    // GET ALL ORDERS
    // ==========================================

    public List<Order> findAll() {
        return orderRepository.findAll();
    }


    // ==========================================
    // GET ORDER BY ID
    // ==========================================

    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }


    // ==========================================
    // SAVE ORDER
    // ==========================================

    public Order save(Order order) {
        return orderRepository.save(order);
    }


    // ==========================================
    // DELETE ORDER
    // ==========================================

    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }


    // ==========================================
    // STUDENT ORDER HISTORY
    // ==========================================

    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserIdOrderByOrderDateDesc(userId);
    }


    // ==========================================
    // PLACE ORDER
    // ==========================================

    @Transactional
    public Order placeOrder(Long userId) {

        // Find user

        User user = userRepository
                .findById(userId)
                .orElse(null);

        if (user == null) {
            return null;
        }


        // Get backend cart

        List<Cart> cartItems =
                cartRepository.findByUserId(userId);

        if (cartItems == null || cartItems.isEmpty()) {
            return null;
        }


        // Calculate subtotal

        double totalAmount = 0.0;

        for (Cart cart : cartItems) {

            if (cart.getFood() == null) {
                continue;
            }

            double price =
                    cart.getFood().getPrice();

            int quantity =
                    cart.getQuantity();

            totalAmount +=
                    price * quantity;
        }


        // Create order

        Order order = new Order();

        order.setUser(user);

        /*
         * Backend stores subtotal here.
         *
         * If you want total including tax
         * in DB, change this calculation.
         */
        order.setTotalAmount(totalAmount);

        order.setStatus("PLACED");

        order.setTokenNumber(
                "SC-" +
                String.format(
                        "%03d",
                        (int) (Math.random() * 900) + 100
                )
        );

        order.setOrderDate(
                LocalDateTime.now()
        );


        // Save order first

        Order savedOrder =
                orderRepository.save(order);


        // Create OrderItems

        for (Cart cart : cartItems) {

            if (cart.getFood() == null) {
                continue;
            }

            OrderItem orderItem =
                    new OrderItem();

            orderItem.setOrder(savedOrder);

            orderItem.setFood(
                    cart.getFood()
            );

            orderItem.setQuantity(
                    cart.getQuantity()
            );

            orderItem.setPrice(
                    cart.getFood().getPrice()
            );

            orderItemRepository.save(orderItem);
        }


        // Make the newly-created items immediately available in the response.
        savedOrder.setItems(orderItemRepository.findByOrderId(savedOrder.getId()));

        // Clear backend cart.
        cartRepository.deleteAll(cartItems);

        return savedOrder;
    }


    // ==========================================
    // CANCEL ORDER
    // ==========================================

    public Order cancelOrder(Long orderId) {

        Order order =
                orderRepository
                        .findById(orderId)
                        .orElse(null);

        if (order == null) {
            return null;
        }


        if (
                "COMPLETED".equalsIgnoreCase(
                        order.getStatus()
                )
        ) {
            return null;
        }


        if (
                "CANCELLED".equalsIgnoreCase(
                        order.getStatus()
                )
        ) {
            return order;
        }


        order.setStatus("CANCELLED");

        return orderRepository.save(order);
    }


    // ==========================================
    // UPDATE STATUS
    // ==========================================

    public Order updateStatus(
            Long orderId,
            String status
    ) {

        Order order =
                orderRepository
                        .findById(orderId)
                        .orElse(null);

        if (order == null) {
            return null;
        }


        if (status == null ||
                status.trim().isEmpty()) {

            return order;
        }


        order.setStatus(
                status.trim().toUpperCase()
        );


        return orderRepository.save(order);
    }


    // ==========================================
    // ASSIGN EMPLOYEE
    // ==========================================

    public Order assignEmployee(
            Long orderId,
            Long employeeId
    ) {

        Order order =
                orderRepository
                        .findById(orderId)
                        .orElse(null);

        if (order == null) {
            return null;
        }


        Employee employee =
                employeeRepository
                        .findById(employeeId)
                        .orElse(null);

        if (employee == null) {
            return null;
        }


        order.setAssignedEmployee(employee);

        return orderRepository.save(order);
    }


    // ==========================================
    // EMPLOYEE ORDERS
    // ==========================================

    public List<Order> findByEmployeeId(
            Long employeeId
    ) {

        return orderRepository
                .findByAssignedEmployeeId(
                        employeeId
                );
    }


    // ==========================================
    // EMPLOYEE PENDING ORDERS
    // ==========================================

    public List<Order> findPendingOrders(
            Long employeeId
    ) {

        return orderRepository
                .findByAssignedEmployeeIdAndStatus(
                        employeeId,
                        "PLACED"
                );
    }


    // ==========================================
    // ADMIN COUNTS
    // ==========================================

    public long getPendingOrderCount() {
        return orderRepository.countByStatus("PLACED");
    }


    public long getCompletedOrderCount() {
        return orderRepository.countByStatus("COMPLETED");
    }


    public long getCancelledOrderCount() {
        return orderRepository.countByStatus("CANCELLED");
    }
}