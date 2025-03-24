package com.example.demo.controllers;

import com.example.demo.models.Watchlist;
import com.example.demo.services.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/watchlist")
public class WatchlistController {

    @Autowired
    private WatchlistService watchlistService;

    // Get all watchlist items for a specific user
    @GetMapping("/{userId}")
    public ResponseEntity<List<Watchlist>> getWatchlistByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(watchlistService.getWatchlistByUserId(userId));
    }

    // Add a share to the user's watchlist
    @PostMapping("/add")
    public ResponseEntity<String> addToWatchlist(
            @RequestParam Integer userId,
            @RequestParam String shareId) {
        String response = watchlistService.addToWatchlist(userId, shareId);
        return ResponseEntity.ok(response);
    }

    // Remove a share from the user's watchlist
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFromWatchlist(
            @RequestParam Integer userId,
            @RequestParam String shareId) {
        String response = watchlistService.removeFromWatchlist(userId, shareId);
        return ResponseEntity.ok(response);
    }

    // Get all watchlist entries
    @GetMapping("/all")
    public ResponseEntity<List<Watchlist>> getAllWatchlists() {
        return ResponseEntity.ok(watchlistService.getAllWatchlists());
    }
}
