package com.example.demo.repos;

import com.example.demo.models.SharePrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SharePriceRepository extends JpaRepository<SharePrice, String> {
}
