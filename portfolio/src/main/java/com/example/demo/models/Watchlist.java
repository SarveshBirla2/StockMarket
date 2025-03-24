package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "watchlist")  // Ensure lowercase table name
public class Watchlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "userid", nullable = false)  // Correcting column name to match DB
    private Integer userId;

    @Column(name = "shareid", nullable = false, length = 10)  // Correcting column name
    private String shareId;

    // Constructors
    public Watchlist() {}

    public Watchlist(Integer userId, String shareId) {
        this.userId = userId;
        this.shareId = shareId;
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
}
