package com.example.smart_canteen_backend.repository;

import com.example.smart_canteen_backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserIdOrderByOrderDateDesc(Long userId);

    List<Order> findByUserId(Long userId);

    List<Order> findByAssignedEmployeeId(Long employeeId);

    List<Order> findByAssignedEmployeeIdAndStatus(
            Long employeeId,
            String status
    );

    long countByStatus(String status);
}