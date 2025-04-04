package com.example.demo.models;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.time.LocalDateTime;
import java.util.*;

@Entity
public class SharePrice {

    @Id
    private String shareCode;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<PriceEntry> priceHistory;

    private double currentPrice;

    // No-args constructor
    public SharePrice() {
        this.priceHistory = new ArrayList<>();
    }

    // Parameterized constructor
    public SharePrice(String shareCode) {
        this.shareCode = shareCode;
        this.priceHistory = new ArrayList<>();
    }

    // Static inner class to represent price entries
    public static class PriceEntry {
        private double price;
        private String timestamp;

        // Default constructor
        public PriceEntry() {}

        public PriceEntry(double price, String timestamp) {
            this.price = price;
            this.timestamp = timestamp;
        }

        // Getters and setters
        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }

    // Getters and setters
    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public List<PriceEntry> getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(List<PriceEntry> priceHistory) {
        this.priceHistory = priceHistory;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void addPrice(double price) {
        // Limit history to 500 entries
        if (priceHistory.size() >= 500) {
            priceHistory.remove(0);
        }

        // Create and add new price entry
        PriceEntry newEntry = new PriceEntry(
            price, 
            LocalDateTime.now().toString()
        );
        priceHistory.add(newEntry);

        // Update current price
        this.currentPrice = price;
    }
    
    
    @Override
    public String toString() {
        return "SharePrice{" +
                "shareCode='" + shareCode + '\'' +
                ", currentPrice=" + currentPrice +
                ", priceHistory=" + priceHistory +
                '}';
    }
}