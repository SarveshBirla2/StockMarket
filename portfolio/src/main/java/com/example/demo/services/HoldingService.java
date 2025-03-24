package com.example.demo.services;

import com.example.demo.models.Holding;
import com.example.demo.repos.HoldingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class HoldingService {

    @Autowired
    private HoldingRepo holdingRepo;

    @Autowired
    private RestTemplate restTemplate; // Injecting RestTemplate
    
    
    private static final String USER_SERVICE_URL = "http://localhost:8083/api/auth/updateAmount";
    
    // Get holdings for a specific user
    public List<Holding> getHoldingsByUserId(Integer userId) {
        return holdingRepo.findByUserId(userId);
    }
    
    
    // Buy shares logic (Insert or Update holding)
    public String buyShares(Integer userId, String shareCode, Double price, Integer quantity) {
        if (quantity <= 0) {
            return "Quantity must be greater than zero.";
        }

        double totalCost = price * quantity;

        // Deduct balance from user account
        boolean balanceUpdated = updateUserBalance(userId, -totalCost); // Negative amount for deduction
        if (!balanceUpdated) {
            return "Insufficient funds for purchase!";
        }

        Holding holding = holdingRepo.findByUserId(userId).stream()
                .filter(h -> h.getShareId().equals(shareCode))
                .findFirst()
                .orElse(new Holding(userId, shareCode, price, 0));

        holding.setPrice(holding.getPrice()+price);
        holding.setQuantity(holding.getQuantity() + quantity);
        holdingRepo.save(holding);
        return "Shares bought successfully!";
    }

    // Sell shares logic (Reduce or Remove holding)
    public String sellShares(Integer userId, String shareCode, Double price, Integer quantity) {
        Optional<Holding> holdingOpt = holdingRepo.findByUserId(userId).stream()
                .filter(h -> h.getShareId().equals(shareCode))
                .findFirst();

        if (holdingOpt.isEmpty()) {
            return "No holdings found for share: " + shareCode;
        }

        Holding holding = holdingOpt.get();
        if (holding.getQuantity() < quantity) {
            return "Not enough shares to sell. Available: " + holding.getQuantity();
        }

        double totalEarnings = price * quantity;

        holding.setQuantity(holding.getQuantity() - quantity);
        if (holding.getQuantity() == 0) {
            holdingRepo.delete(holding);
        } else {
            holdingRepo.save(holding);
        }

        // Credit balance to user account
        updateUserBalance(userId, totalEarnings); // Positive amount for adding money
        return quantity + " shares of " + shareCode + " sold successfully!";
    }

    // Method to call User Microservice for balance update
    private boolean updateUserBalance(Integer userId, double amount) {
        try {
            Map<String, Object> request = new HashMap<>();
            request.put("userId", userId); // Adjust if needed
            request.put("updateAmount", amount);

            restTemplate.postForEntity(USER_SERVICE_URL, request, String.class);
            return true;
        } catch (Exception e) {
        	System.out.println(e);
            return false;
        }
    }

    // Get a specific holding for a user and share
    public Holding getHoldingByUserAndShare(Integer userId, String shareCode) {
        return holdingRepo.findByUserId(userId).stream()
                .filter(h -> h.getShareId().equals(shareCode))
                .findFirst()
                .orElse(null); // Return null instead of throwing exception
    }

    // Get all holdings
    public List<Holding> getAllHoldings() {
        return holdingRepo.findAll();
    }

    // Delete a holding by ID
    public String deleteHolding(Integer id) {
        if (!holdingRepo.existsById(id)) {
            return "Holding with ID " + id + " not found!";
        }
        holdingRepo.deleteById(id);
        return "Holding deleted successfully!";
    }
}
