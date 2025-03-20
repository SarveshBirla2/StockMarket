package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Table(name = "Share")
public class Share {

    @Id
    @Column(name = "code", length = 10, nullable = false, unique = true)
    private String code; // Stock code (UPPERCASE)

    @Column(name = "companyname", nullable = false)
    private String companyName;

    @Column(name = "minprice", nullable = false)
    private Double minPrice;

    @Column(name = "maxprice", nullable = false)
    private Double maxPrice;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    // Constructors
    public Share() {}

    public Share(String code, String companyName, Double minPrice, Double maxPrice, Integer quantity) {
        this.code = code.toUpperCase();
        this.companyName = companyName;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.quantity = quantity;
    }

    // Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code.toUpperCase();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
