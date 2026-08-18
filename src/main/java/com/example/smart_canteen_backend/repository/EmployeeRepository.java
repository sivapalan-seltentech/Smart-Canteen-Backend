package com.example.smart_canteen_backend.repository;

import com.example.smart_canteen_backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmailIgnoreCase(String email);

    Optional<Employee> findByUsernameIgnoreCase(String username);

    Optional<Employee> findByEmployeeIdIgnoreCase(String employeeId);

    boolean existsByUsernameIgnoreCase(String username);

    boolean existsByEmployeeIdIgnoreCase(String employeeId);

    boolean existsByEmailIgnoreCase(String email);
}