package com.example.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.Share;
import com.example.demo.services.ShareService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shares")
public class ShareController {

    @Autowired
    private ShareService shareService;

    // Add a new share
    @PostMapping("/add")
    public Share addShare(@RequestBody Share share) {
        return shareService.addShare(share);
    }

    // Update share quantity
    @PutMapping("/updateQuantity")
    public Share updateShareQuantity(@RequestParam String code, @RequestParam Integer newQuantity) {
        return shareService.updateShareQuantity(code, newQuantity);
    }

    // Delete a share
    @DeleteMapping("/delete")
    public String deleteShare(@RequestParam String code) {
        shareService.deleteShare(code);
        return "Share deleted successfully!";
    }

    // View all shares
    @GetMapping("/view")
    public List<Share> viewShares() {
        return shareService.getAllShares();
    }
    
    @GetMapping("/{code}")
    public Optional<Share> getShareByCode(@PathVariable String code) {
        return shareService.getShareByCode(code);
    }

    
    @GetMapping("/{code}/price")
    public double getSharePrice(@PathVariable String code) {
        return shareService.getDynamicSharePrice(code);
    }
    
}
