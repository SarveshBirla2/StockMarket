package com.example.demo.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id;

    @Column(name = "Name", nullable = false, length = 100)
    private String name;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "DOB", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dob;

    @Column(name = "Balance", nullable = true)
    private Double balance;

  
    public User() {}

    public User(String name, String password, Date dob, Double balance) {
        this.name = name;
        this.password = password;
        this.dob = dob;
        this.balance = balance;
    }
    
    public User(String name, String password, Date dob) {
        this.name = name;
        this.password = password;
        this.dob = dob;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
