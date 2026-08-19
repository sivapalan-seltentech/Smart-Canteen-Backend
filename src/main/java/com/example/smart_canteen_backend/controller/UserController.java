package com.example.smart_canteen_backend.controller;

import com.example.smart_canteen_backend.entity.User;
import com.example.smart_canteen_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    // =========================================
    // GET ALL
    // =========================================

    @GetMapping
    public ResponseEntity<List<User>> findAll() {

        return ResponseEntity.ok(
                userService.findAll()
        );
    }

    // =========================================
    // GET BY ID
    // =========================================

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(
            @PathVariable Long id
    ) {

        User user =
                userService.findById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    // =========================================
    // CREATE
    // =========================================

    @PostMapping
    public ResponseEntity<User> save(
            @RequestBody User user
    ) {

        return ResponseEntity.ok(
                userService.save(user)
        );
    }

    // =========================================
    // UPDATE
    // =========================================

    @PutMapping("/{id}")
    public ResponseEntity<User> update(
            @PathVariable Long id,
            @RequestBody User user
    ) {

        User updated =
                userService.update(id, user);

        return ResponseEntity.ok(updated);
    }

    // =========================================
    // DELETE
    // =========================================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(
            @PathVariable Long id
    ) {

        userService.deleteById(id);

        return ResponseEntity.ok(
                "User deleted successfully"
        );
    }
}