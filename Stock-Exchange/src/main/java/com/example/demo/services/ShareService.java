package com.example.demo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.models.Share;
import com.example.demo.repos.ShareRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class ShareService {

    @Autowired
    private ShareRepository shareRepository;

    // Add a new share
    public Share addShare(Share share) {
        share.setCode(share.getCode().toUpperCase()); // Ensure uppercase
        return shareRepository.save(share);
    }

    // Update share quantity
    public Share updateShareQuantity(String code, Integer newQuantity) {
        Optional<Share> optionalShare = shareRepository.findById(code.toUpperCase());
        if (optionalShare.isPresent()) {
            Share share = optionalShare.get();
            share.setQuantity(newQuantity);
            return shareRepository.save(share);
        }
        throw new RuntimeException("Share with code " + code + " not found");
    }

    // Delete a share
    public void deleteShare(String code) {
        shareRepository.deleteById(code.toUpperCase());
    }

    // Get all shares
    public List<Share> getAllShares() {
        return shareRepository.findAll();
    }
    
    
    public Optional<Share> getShareByCode(String code) {
        return shareRepository.findById(code.toUpperCase());
    }

    public double getDynamicSharePrice(String code) {
        Optional<Share> shareOptional = shareRepository.findById(code.toUpperCase());
        if (shareOptional.isPresent()) {
            Share share = shareOptional.get();
            double minPrice = share.getMinPrice();
            double maxPrice = share.getMaxPrice();

            // Generate a random price between minPrice and maxPrice
            Random random = new Random();
            return minPrice + (maxPrice - minPrice) * random.nextDouble()* 0.01;
        } else {
            throw new RuntimeException("Share with code " + code + " not found!");
        }
    }
    
    
    public ResponseEntity<Map<String, Object>> sellShares(String shareCode, Integer quantity) {
        Map<String, Object> response = new HashMap<>();
        Optional<Share> optionalShare = shareRepository.findById(shareCode.toUpperCase());

        if (optionalShare.isPresent()) {
            Share share = optionalShare.get();

            // Check if selling exceeds available quantity
            if (share.getSoldShares() + quantity > share.getQuantity()) {
                response.put("status", "error");
                response.put("message", "Sold shares cannot exceed available quantity.");
                return ResponseEntity.badRequest().body(response);
            }

            // Update soldShares
            share.setSoldShares(share.getSoldShares() + quantity);
            shareRepository.save(share);

            // Calculate remaining shares
            int remainingShares = share.getQuantity() - share.getSoldShares();
            response.put("status", "success");
            response.put("message", "Shares sold successfully.");
            response.put("remainingShares", remainingShares);
            return ResponseEntity.ok(response);
        }

        response.put("status", "error");
        response.put("message", "Share with code '" + shareCode + "' not found.");
        return ResponseEntity.badRequest().body(response);
    }
    
    
}
