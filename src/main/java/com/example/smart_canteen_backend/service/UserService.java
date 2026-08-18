package com.example.smart_canteen_backend.service;

import com.example.smart_canteen_backend.entity.User;
import com.example.smart_canteen_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User update(Long id, User user) {
        User existing = userRepository.findById(id).orElse(null);
        if (existing == null) return null;
        existing.setName(user.getName());
        existing.setEmail(user.getEmail());
        existing.setPhone(user.getPhone());
        existing.setRole(user.getRole());
        existing.setStudentId(user.getStudentId());
        existing.setUsername(user.getUsername());
        existing.setDepartment(user.getDepartment());
        existing.setYear(user.getYear());
        if (user.getPassword() != null && !user.getPassword().isBlank()) existing.setPassword(user.getPassword());
        return userRepository.save(existing);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
