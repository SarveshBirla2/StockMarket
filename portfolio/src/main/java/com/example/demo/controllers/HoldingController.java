package com.example.demo.controllers;

import com.example.demo.models.Holding;
import com.example.demo.services.HoldingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/holdings")
public class HoldingController {

    @Autowired
    private HoldingService holdingService;
    
    

    // Get holdings for a specific user
    @GetMapping("/{userId}")
    public ResponseEntity<List<Holding>> getHoldingsByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(holdingService.getHoldingsByUserId(userId));
    }

    // Buy shares (Add or Update Holding)
    @PostMapping("/buy")
    public ResponseEntity<String> buyShares(
            @RequestParam Integer userId,
            @RequestParam String shareCode,
            @RequestParam Double price,
            @RequestParam Integer quantity) {
        String response = holdingService.buyShares(userId, shareCode, price, quantity);
        return ResponseEntity.ok(response);
    }

    // Sell shares (Update or Remove Holding)
    @PostMapping("/sell")
    public ResponseEntity<String> sellShares(
            @RequestParam Integer userId,
            @RequestParam String shareCode,
            @RequestParam Double price,
            @RequestParam Integer quantity) {
        String response = holdingService.sellShares(userId, shareCode, price, quantity);
        return ResponseEntity.ok(response);
    }

    // Get holdings of a specific user for a specific share
    @GetMapping("/{userId}/{shareCode}")
    public ResponseEntity<?> getHoldingByUserAndShare(
            @PathVariable Integer userId,
            @PathVariable String shareCode) {
        Holding holding = holdingService.getHoldingByUserAndShare(userId, shareCode);
        return ResponseEntity.ok(holding);
    }

    // Get all holdings
    @GetMapping("/all")
    public ResponseEntity<List<Holding>> getAllHoldings() {
        return ResponseEntity.ok(holdingService.getAllHoldings());
    }

    // Delete a holding by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteHolding(@PathVariable Integer id) {
        String response = holdingService.deleteHolding(id);
        return ResponseEntity.ok(response);
    }
}
