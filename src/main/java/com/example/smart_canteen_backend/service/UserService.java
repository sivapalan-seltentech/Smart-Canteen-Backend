package com.example.smart_canteen_backend.service;

import com.example.smart_canteen_backend.entity.User;
import com.example.smart_canteen_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // =========================================
    // GET ALL USERS
    // =========================================

    public List<User> findAll() {
        return userRepository.findAll();
    }

    // =========================================
    // GET USER BY ID
    // =========================================

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // =========================================
    // CREATE USER
    // =========================================

    public User save(User user) {
        return userRepository.save(user);
    }

    // =========================================
    // UPDATE USER
    // =========================================

    public User update(Long id, User user) {

        User existing = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + id)
                );

        // -----------------------------------------
        // BASIC FIELDS
        // -----------------------------------------

        if (user.getName() != null &&
                !user.getName().isBlank()) {

            existing.setName(
                    user.getName().trim()
            );
        }

        // -----------------------------------------
        // USERNAME
        // -----------------------------------------

        if (user.getUsername() != null &&
                !user.getUsername().isBlank()) {

            existing.setUsername(
                    user.getUsername().trim()
            );
        }

        // -----------------------------------------
        // STUDENT ID
        // -----------------------------------------

        if (user.getStudentId() != null &&
                !user.getStudentId().isBlank()) {

            existing.setStudentId(
                    user.getStudentId().trim()
            );
        }

        // -----------------------------------------
        // EMAIL
        // -----------------------------------------

        if (user.getEmail() != null &&
                !user.getEmail().isBlank()) {

            existing.setEmail(
                    user.getEmail().trim()
            );
        }

        // -----------------------------------------
        // PHONE
        // -----------------------------------------

        if (user.getPhone() != null) {

            existing.setPhone(
                    user.getPhone().trim()
            );
        }

        // -----------------------------------------
        // DEPARTMENT
        // -----------------------------------------

        if (user.getDepartment() != null &&
                !user.getDepartment().isBlank()) {

            existing.setDepartment(
                    user.getDepartment().trim()
            );
        }

        // -----------------------------------------
        // YEAR
        // -----------------------------------------

        if (user.getYear() != null &&
                !user.getYear().isBlank()) {

            existing.setYear(
                    user.getYear().trim()
            );
        }

        // -----------------------------------------
        // ROLE
        // -----------------------------------------
        // Don't overwrite existing role with null.

        if (user.getRole() != null &&
                !user.getRole().isBlank()) {

            existing.setRole(
                    user.getRole().trim()
            );
        }

        // -----------------------------------------
        // PASSWORD
        // -----------------------------------------
        // Password is WRITE_ONLY in entity.
        // If frontend doesn't send password,
        // getPassword() will be null.
        //
        // Therefore keep old password.

        if (user.getPassword() != null &&
                !user.getPassword().isBlank()) {

            existing.setPassword(
                    user.getPassword()
            );
        }

        // -----------------------------------------
        // SAVE
        // -----------------------------------------

        try {

            return userRepository.saveAndFlush(existing);

        } catch (DataIntegrityViolationException e) {

            throw new RuntimeException(
                    "Username, Student ID or Email already exists."
            );
        }
    }

    // =========================================
    // DELETE USER
    // =========================================

    public void deleteById(Long id) {

        if (!userRepository.existsById(id)) {
            throw new RuntimeException(
                    "User not found with id: " + id
            );
        }

        userRepository.deleteById(id);
    }
}