package com.example.demo.controller;

import com.example.demo.services.SharePriceService;

import ch.qos.logback.core.model.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@CrossOrigin("*")
public class SharePriceController {

    @Autowired
    private SharePriceService sharePriceService;

    // REST API to get all share prices
//    @GetMapping
//    public ResponseEntity<?> getAllSharePrices() {
//        return sharePriceService.getAllSharePrices();
//    }
//
//    // REST API to manually update prices
//    @PostMapping("/update")
//    public ResponseEntity<?> updateSharePrices() {
//        return sharePriceService.fetchAndUpdateShares();
//    }

    // WebSocket: Get all share prices
    
    @MessageMapping("/prices")
    @SendTo("/topic/share-prices")
    public ResponseEntity<?> sendAllSharePrices() {
    	System.out.println("------------Share Prices Called via Socket \n");
        return sharePriceService.getAllSharePrices();
    }
    
    
}
