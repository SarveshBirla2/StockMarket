package com.example.demo.repos;

import com.example.demo.models.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchlistRepo extends JpaRepository<Watchlist, Integer> {

    // Fetch all watchlist items for a specific user
    List<Watchlist> findByUserId(Integer userId);

    // Check if a share is already in the user's watchlist
    boolean existsByUserIdAndShareId(Integer userId, String shareId);

    // Remove a specific share from a user's watchlist
    void deleteByUserIdAndShareId(Integer userId, String shareId);
}
