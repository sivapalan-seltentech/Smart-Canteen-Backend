package com.example.smart_canteen_backend.service;

import com.example.smart_canteen_backend.entity.Employee;
import com.example.smart_canteen_backend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // =========================
    // GET ALL
    // =========================

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    // =========================
    // GET BY ID
    // =========================

    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    // =========================
    // CREATE
    // =========================

    public Employee save(Employee employee) {

        if (employee.getName() == null ||
                employee.getName().trim().isEmpty()) {
            throw new RuntimeException("Employee name is required");
        }

        if (employee.getEmployeeId() == null ||
                employee.getEmployeeId().trim().isEmpty()) {
            throw new RuntimeException("Employee ID is required");
        }

        if (employee.getUsername() == null ||
                employee.getUsername().trim().isEmpty()) {
            throw new RuntimeException("Username is required");
        }

        if (employee.getPassword() == null ||
                employee.getPassword().trim().isEmpty()) {
            throw new RuntimeException("Password is required");
        }

        if (employeeRepository.existsByUsernameIgnoreCase(
                employee.getUsername())) {

            throw new RuntimeException(
                    "Employee username already exists"
            );
        }

        if (employeeRepository.existsByEmployeeIdIgnoreCase(
                employee.getEmployeeId())) {

            throw new RuntimeException(
                    "Employee ID already exists"
            );
        }

        if (employee.getEmail() != null &&
                !employee.getEmail().trim().isEmpty() &&
                employeeRepository.existsByEmailIgnoreCase(
                        employee.getEmail())) {

            throw new RuntimeException(
                    "Employee email already exists"
            );
        }

        employee.setName(employee.getName().trim());
        employee.setEmployeeId(employee.getEmployeeId().trim());
        employee.setUsername(employee.getUsername().trim());

        if (employee.getEmail() != null) {
            employee.setEmail(employee.getEmail().trim());
        }

        return employeeRepository.save(employee);
    }

    // =========================
    // UPDATE
    // =========================

    public Employee update(Long id, Employee employee) {

        Employee existingEmployee =
                employeeRepository.findById(id).orElse(null);

        if (existingEmployee == null) {
            throw new RuntimeException("Employee not found");
        }

        existingEmployee.setName(employee.getName());
        existingEmployee.setEmployeeId(employee.getEmployeeId());
        existingEmployee.setUsername(employee.getUsername());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setPhone(employee.getPhone());

        if (employee.getPassword() != null &&
                !employee.getPassword().trim().isEmpty()) {

            existingEmployee.setPassword(employee.getPassword());
        }

        return employeeRepository.save(existingEmployee);
    }

    // =========================
    // DELETE
    // =========================

    public void deleteById(Long id) {

        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found");
        }

        employeeRepository.deleteById(id);
    }
}