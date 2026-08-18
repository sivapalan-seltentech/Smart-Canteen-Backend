package com.example.smart_canteen_backend.controller;

import com.example.smart_canteen_backend.entity.Employee;
import com.example.smart_canteen_backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:5173")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // =========================
    // GET ALL
    // =========================

    @GetMapping
    public ResponseEntity<List<Employee>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    // =========================
    // GET ONE
    // =========================

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findById(
            @PathVariable Long id
    ) {

        Employee employee =
                employeeService.findById(id);

        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(employee);
    }

    // =========================
    // CREATE
    // =========================

    @PostMapping
    public ResponseEntity<Employee> save(
            @RequestBody Employee employee
    ) {

        Employee saved =
                employeeService.save(employee);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    // =========================
    // UPDATE
    // =========================

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(
            @PathVariable Long id,
            @RequestBody Employee employee
    ) {

        Employee updated =
                employeeService.update(id, employee);

        return ResponseEntity.ok(updated);
    }

    // =========================
    // DELETE
    // =========================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(
            @PathVariable Long id
    ) {

        employeeService.deleteById(id);

        return ResponseEntity.ok(
                java.util.Map.of(
                        "message",
                        "Employee deleted successfully"
                )
        );
    }
}