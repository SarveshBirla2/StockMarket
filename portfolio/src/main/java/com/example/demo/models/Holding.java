package com.example.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Holding")
public class Holding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "UserID", nullable = false)
    private Integer userId;  // No direct relation to User entity

    @Column(name = "ShareID", nullable = false, length = 10)
    private String shareId;  // No direct relation to Share entity

    @Column(name = "Price", nullable = false)
    private Double price;  // Changed from BigDecimal to Double

    @Column(name = "Quantity", nullable = false)
    private int quantity;

    // Constructors
    public Holding() {}

    public Holding(Integer userId, String shareId, Double price, int quantity) {
        this.userId = userId;
        this.shareId = shareId;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
