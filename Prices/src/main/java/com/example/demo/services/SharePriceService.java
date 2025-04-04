package com.example.demo.services;

import com.example.demo.models.SharePrice;
import com.example.demo.repos.SharePriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class SharePriceService {

    @Autowired
    private SharePriceRepository sharePriceRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private RestTemplate restTemplate;

    private final Random random = new Random();

    @Scheduled(fixedRate = 5000)
    public ResponseEntity<?> fetchAndUpdateShares() {
//        System.out.println("Executing fetchAndUpdateShares...");

        String sharesServiceUrl = "http://localhost:8082/shares/view";

        try {
            List<Map<String, Object>> shares = restTemplate.getForObject(sharesServiceUrl, List.class);
            if (shares == null || shares.isEmpty()) {
                return ResponseEntity.status(404).body(Collections.singletonMap("message", "No shares found"));
            }

            List<SharePrice> updatedShares = new ArrayList<>();
            for (Map<String, Object> shareData : shares) {
                String shareCode = (String) shareData.get("code");
                double minPrice = (double) shareData.get("minPrice");
                double maxPrice = (double) shareData.get("maxPrice");

                // Generate random price
                double newPrice = minPrice + (maxPrice - minPrice) * random.nextDouble();

                // Find or create SharePrice
                SharePrice share = sharePriceRepository.findById(shareCode)
                    .orElse(new SharePrice(shareCode));

                // Add new price
                share.addPrice(newPrice);
                sharePriceRepository.save(share);
                updatedShares.add(share);
//                System.out.println(share);
            }

            // Send WebSocket message
           
            messagingTemplate.convertAndSend("/topic/share-prices", updatedShares);

            return ResponseEntity.ok(updatedShares);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                .body(Collections.singletonMap("error", "Failed to fetch shares: " + e.getMessage()));
        }
    }

    public ResponseEntity<?> getAllSharePrices() {
        try {
            List<SharePrice> allShares = sharePriceRepository.findAll();
            return ResponseEntity.ok(allShares);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                .body(Collections.singletonMap("error", "Failed to fetch share prices"));
        }
    }
}