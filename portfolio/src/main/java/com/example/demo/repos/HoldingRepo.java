package com.example.demo.repos;

import com.example.demo.models.Holding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HoldingRepo extends JpaRepository<Holding, Integer> {

    // Fetch holdings for a specific user
    List<Holding> findByUserId(Integer userId);

    // Fetch holdings for a specific share
    List<Holding> findByShareId(String shareId);
}
