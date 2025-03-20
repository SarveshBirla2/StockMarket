package com.example.demo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Share;
import com.example.demo.repos.ShareRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ShareService {

    @Autowired
    private ShareRepository shareRepository;

    // Add a new share
    public Share addShare(Share share) {
        share.setCode(share.getCode().toUpperCase()); // Ensure uppercase
        return shareRepository.save(share);
    }

    // Update share quantity
    public Share updateShareQuantity(String code, Integer newQuantity) {
        Optional<Share> optionalShare = shareRepository.findById(code.toUpperCase());
        if (optionalShare.isPresent()) {
            Share share = optionalShare.get();
            share.setQuantity(newQuantity);
            return shareRepository.save(share);
        }
        throw new RuntimeException("Share with code " + code + " not found");
    }

    // Delete a share
    public void deleteShare(String code) {
        shareRepository.deleteById(code.toUpperCase());
    }

    // Get all shares
    public List<Share> getAllShares() {
        return shareRepository.findAll();
    }
    
    
    public Optional<Share> getShareByCode(String code) {
        return shareRepository.findById(code.toUpperCase());
    }

    public double getDynamicSharePrice(String code) {
        Optional<Share> shareOptional = shareRepository.findById(code.toUpperCase());
        if (shareOptional.isPresent()) {
            Share share = shareOptional.get();
            double minPrice = share.getMinPrice();
            double maxPrice = share.getMaxPrice();

            // Generate a random price between minPrice and maxPrice
            Random random = new Random();
            return minPrice + (maxPrice - minPrice) * random.nextDouble();
        } else {
            throw new RuntimeException("Share with code " + code + " not found!");
        }
    }
    
    
}
