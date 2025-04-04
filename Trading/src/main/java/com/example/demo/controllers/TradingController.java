package com.example.demo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.services.TradingService;

@RestController
@RequestMapping("/trade")
@CrossOrigin("*")
public class TradingController {

    @Autowired
    TradingService ts;

//    @PostMapping("/buy")
//    public String buyShares(@RequestParam Integer userId, @RequestParam String shareCode, @RequestParam Integer quantity) {
//    	System.out.println("1234");
//        return ts.buy(userId, shareCode, quantity);
//    }
    
    @PostMapping("/buy")
    public String buyShares(@RequestBody Map<String, Object> requestData) {
    	Integer userId = (Integer)requestData.get("userId");
    	String shareCode = (String)requestData.get("shareCode");
    	Integer quantity = (Integer)requestData.get("quantity");
    	System.out.println("1234");
        return ts.buy(userId, shareCode, quantity);
    }
//
//    @PostMapping("/sell")
//    public String sellShares(@RequestParam Integer userId, @RequestParam String shareCode, @RequestParam Integer quantity) {
//        return ts.sell(userId, shareCode, quantity);
//    }
    
    @PostMapping("/sell")
    public String sellShares(@RequestBody Map<String, Object> requestData) {
    	Integer userId = (Integer)requestData.get("userId");
    	String shareCode = (String)requestData.get("shareCode");
    	Integer quantity = (Integer)requestData.get("quantity");
        return ts.sell(userId, shareCode, quantity);
    }
}
