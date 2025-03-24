package com.example.demo.services;

import com.example.demo.models.Watchlist;
import com.example.demo.repos.WatchlistRepo;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchlistService {

    @Autowired
    private WatchlistRepo watchlistRepo;

    // Get all watchlist items for a specific user
    public List<Watchlist> getWatchlistByUserId(Integer userId) {
        return watchlistRepo.findByUserId(userId);
    }

    // Add a share to the user's watchlist
    public String addToWatchlist(Integer userId, String shareId) {
        if (watchlistRepo.existsByUserIdAndShareId(userId, shareId)) {
            return "Share already in watchlist!";
        }
        Watchlist watchlist = new Watchlist(userId, shareId);
        watchlistRepo.save(watchlist);
        return "Share added to watchlist successfully!";
    }

    // Remove a share from the user's watchlist
    @Transactional
    public String removeFromWatchlist(Integer userId, String shareId) {
        if (!watchlistRepo.existsByUserIdAndShareId(userId, shareId)) {
            return "Share not found in watchlist!";
        }
        watchlistRepo.deleteByUserIdAndShareId(userId, shareId);
        return "Share removed from watchlist successfully!";
    }

    // Get all watchlist entries
    public List<Watchlist> getAllWatchlists() {
        return watchlistRepo.findAll();
    }
}
