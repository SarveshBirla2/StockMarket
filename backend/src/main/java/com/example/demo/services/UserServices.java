package com.example.demo.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repos.UserDao;

@Service 
public class UserServices {

    @Autowired
    private UserDao userDao;

    public ResponseEntity<Map<String, String>> login(String name, String password) {
        Optional<User> userOpt = userDao.findByName(name);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid Account Number or Password"));
        }

        User obj = userOpt.get();
        if (obj.getPassword().equals(password)) {
            return ResponseEntity.ok(Map.of("message", "Login successful", "user", String.valueOf(obj.getId())));
        }

        return ResponseEntity.status(401).body(Map.of("message", "Invalid Account Number or Password"));
    }

    public ResponseEntity<Map<String, String>> register(User user) {
        try {
            userDao.save(user);
            return ResponseEntity.ok(Map.of("message", "Account created successfully", "status", "success"));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("message", e.getMessage(), "status", "fail"));
        }
    }

    public ResponseEntity<Map<String, String>> changePassword(String name, String oldPassword, String newPassword) {
        Optional<User> userOpt = userDao.findByName(name);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid Account Number or Old Password"));
        }

        User obj = userOpt.get();
        if (obj.getPassword().equals(oldPassword)) {
            obj.setPassword(newPassword);
            userDao.save(obj);
            return ResponseEntity.ok(Map.of("message", "Password updated successfully"));
        }

        return ResponseEntity.status(401).body(Map.of("message", "Invalid Account Number or Old Password"));
    }

    public ResponseEntity<Map<String, String>> forgetPassword(String name, Date dob) {
        Optional<User> userOpt = userDao.findByName(name);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid Account Name or DOB"));
        }

        User obj = userOpt.get();
        if (obj.getDob().equals(dob)) {
            return ResponseEntity.ok(Map.of("message", obj.getPassword()));
        }

        return ResponseEntity.status(401).body(Map.of("message", "Invalid Account Name or DOB"));
    }

    public ResponseEntity<Map<String, String>> performTransaction(Integer userId, double updateAmount) {
    	Optional<User> userOpt = userDao.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "User not found"));
        }
        
        User user = userOpt.get();
        double newBalance = user.getBalance() + updateAmount;

        if (newBalance < 0) {
            return ResponseEntity.badRequest().body(Map.of("message", "Insufficient balance"));
        }

        user.setBalance(newBalance);
        userDao.save(user);

        return ResponseEntity.ok(Map.of("message", "Balance updated successfully", "newBalance", String.valueOf(newBalance)));
    }

    public ResponseEntity<Double> getBalance(Integer userId) {
        return userDao.findById(userId)
                .map(user -> ResponseEntity.ok(user.getBalance()))
                .orElse(ResponseEntity.notFound().build());
    }
    
    
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

}
