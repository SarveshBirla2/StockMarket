package com.example.demo.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.services.UserServices;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserServices userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam String name, @RequestParam String password) {
        return userService.login(name, password);
    }
    
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
        return userService.register(user);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<Map<String, String>> changePassword(@RequestParam String name, @RequestParam String oldPassword, @RequestParam String newPassword) {
        return userService.changePassword(name, oldPassword, newPassword);
    }

    @PostMapping("/forgetPassword")
    public ResponseEntity<Map<String, String>> forgetPassword(@RequestBody Map<String, Object> requestData) {
        String name = (String) requestData.get("name");
        String dobStr = (String) requestData.get("dob");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dob;
        
        try {
            dob = dateFormat.parse(dobStr);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid Date Format"));
        }

        return userService.forgetPassword(name, dob);
    }
    
    @PostMapping("/updateAmount")
    public ResponseEntity<Map<String, String>> updateBalance(@RequestBody Map<String, Object> requestData) {
    	Integer userId = (Integer) requestData.get("userId"); 
        double updateAmount;
        
        try {
            updateAmount = Double.parseDouble(requestData.get("updateAmount").toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid Amount Format"));
        }

        return userService.performTransaction(userId, updateAmount);
    }

    
    
    @GetMapping("/{userId}/balance")
    public ResponseEntity<Double> getBalance(@PathVariable Integer userId){
    	return userService.getBalance(userId);
    }
    
}