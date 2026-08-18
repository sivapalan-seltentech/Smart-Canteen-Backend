package com.example.smart_canteen_backend.service;

import com.example.smart_canteen_backend.entity.Employee;
import com.example.smart_canteen_backend.entity.User;
import com.example.smart_canteen_backend.repository.EmployeeRepository;
import com.example.smart_canteen_backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JwtService jwtService;

    // =====================================================
    // STUDENT REGISTER
    // =====================================================

    public User registerStudent(User user) {

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username is required.");
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is required.");
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password is required.");
        }

        if (userRepository.existsByUsernameIgnoreCase(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists.");
        }

        if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists.");
        }

        if (user.getStudentId() != null
                && !user.getStudentId().isBlank()
                && userRepository.existsByStudentIdIgnoreCase(user.getStudentId())) {

            throw new IllegalArgumentException("Student ID already exists.");
        }

        user.setRole("STUDENT");

        return userRepository.save(user);
    }

    // =====================================================
    // LOGIN
    // =====================================================

    public Map<String, Object> login(
            String identifier,
            String password) {

        if (identifier == null || identifier.isBlank()) {
            throw new IllegalArgumentException(
                    "Username, email or student ID is required."
            );
        }

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException(
                    "Password is required."
            );
        }

        // -------------------------------------------------
        // CHECK NORMAL USER / STUDENT / ADMIN
        // -------------------------------------------------

        Optional<User> userOpt =
                userRepository.findByUsernameIgnoreCase(identifier);

        if (userOpt.isEmpty()) {
            userOpt =
                    userRepository.findByEmailIgnoreCase(identifier);
        }

        if (userOpt.isEmpty()) {
            userOpt =
                    userRepository.findByStudentIdIgnoreCase(identifier);
        }

        if (userOpt.isPresent()) {

            User user = userOpt.get();

            if (!password.equals(user.getPassword())) {
                throw invalidCredentials();
            }

            String role = normalizeRole(user.getRole());

            String token =
                    jwtService.generateToken(
                            user.getUsername(),
                            role
                    );

            return response(
                    token,
                    sanitizeUser(user)
            );
        }

        // -------------------------------------------------
        // CHECK EMPLOYEE
        // -------------------------------------------------

        Optional<Employee> employeeOpt =
                employeeRepository.findByUsernameIgnoreCase(identifier);

        if (employeeOpt.isEmpty()) {
            employeeOpt =
                    employeeRepository.findByEmailIgnoreCase(identifier);
        }

        if (employeeOpt.isPresent()) {

            Employee employee = employeeOpt.get();

            if (!password.equals(employee.getPassword())) {
                throw invalidCredentials();
            }

            String subject;

            if (employee.getUsername() != null
                    && !employee.getUsername().isBlank()) {

                subject = employee.getUsername();

            } else {

                subject = employee.getEmail();
            }

            String token =
                    jwtService.generateToken(
                            subject,
                            "EMPLOYEE"
                    );

            return response(
                    token,
                    sanitizeEmployee(employee)
            );
        }

        throw invalidCredentials();
    }

    // =====================================================
    // CURRENT USER
    // =====================================================

    public Map<String, Object> currentUser(String token) {

        String role =
                normalizeRole(
                        jwtService.extractRole(token)
                );

        String subject =
                jwtService.extractUsername(token);

        // -------------------------------------------------
        // EMPLOYEE
        // -------------------------------------------------

        if ("EMPLOYEE".equals(role)) {

            Optional<Employee> employeeOpt =
                    employeeRepository.findByUsernameIgnoreCase(subject);

            if (employeeOpt.isEmpty()) {

                employeeOpt =
                        employeeRepository.findByEmailIgnoreCase(subject);
            }

            Employee employee =
                    employeeOpt.orElseThrow(
                            () -> new IllegalArgumentException(
                                    "Employee not found."
                            )
                    );

            if (!jwtService.isTokenValid(token, subject)) {
                throw new IllegalArgumentException(
                        "Invalid or expired authentication token."
                );
            }

            return sanitizeEmployee(employee);
        }

        // -------------------------------------------------
        // ADMIN / STUDENT
        // -------------------------------------------------

        User user =
                userRepository
                        .findByUsernameIgnoreCase(subject)
                        .orElseThrow(
                                () -> new IllegalArgumentException(
                                        "User not found."
                                )
                        );

        if (!jwtService.isTokenValid(
                token,
                user.getUsername())) {

            throw new IllegalArgumentException(
                    "Invalid or expired authentication token."
            );
        }

        return sanitizeUser(user);
    }

    // =====================================================
    // INVALID CREDENTIALS
    // =====================================================

    private IllegalArgumentException invalidCredentials() {

        return new IllegalArgumentException(
                "Invalid username, email, student ID or password."
        );
    }

    // =====================================================
    // NORMALIZE ROLE
    // =====================================================

    private String normalizeRole(String role) {

        if (role == null || role.isBlank()) {
            return "STUDENT";
        }

        return role.toUpperCase();
    }

    // =====================================================
    // RESPONSE
    // =====================================================

    private Map<String, Object> response(
            String token,
            Map<String, Object> user) {

        Map<String, Object> result =
                new LinkedHashMap<>();

        result.put("token", token);
        result.put(
                "expiresInMs",
                jwtService.getExpirationMs()
        );
        result.put("user", user);

        return result;
    }

    // =====================================================
    // SANITIZE USER
    // =====================================================

    private Map<String, Object> sanitizeUser(User user) {

        Map<String, Object> data =
                new LinkedHashMap<>();

        data.put("id", user.getId());
        data.put("name", user.getName());
        data.put("email", user.getEmail());
        data.put("phone", user.getPhone());
        data.put(
                "role",
                normalizeRole(user.getRole())
        );
        data.put("studentId", user.getStudentId());
        data.put("username", user.getUsername());
        data.put("createdAt", user.getCreatedAt());
        data.put("department", user.getDepartment());
        data.put("year", user.getYear());

        return data;
    }

    // =====================================================
    // SANITIZE EMPLOYEE
    // =====================================================

    private Map<String, Object> sanitizeEmployee(
            Employee employee) {

        Map<String, Object> data =
                new LinkedHashMap<>();

        data.put("id", employee.getId());
        data.put("name", employee.getName());
        data.put("email", employee.getEmail());
        data.put("username", employee.getUsername());
        data.put("employeeId", employee.getEmployeeId());
        data.put("phone", employee.getPhone());
        data.put("role", "EMPLOYEE");

        return data;
    }
}