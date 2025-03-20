package com.example.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TradingService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String HOLDING_SERVICE_URL = "http://localhost:8082/holdings";
    private static final String USER_SERVICE_URL = "http://localhost:8081/users";
    private static final String SHARE_SERVICE_URL = "http://localhost:8083/shares";

    public String buy(Integer userId, String shareCode, Integer quantity) {
       
        Double userBalance = restTemplate.getForObject(USER_SERVICE_URL + "/" + userId + "/balance", Double.class);

     
        Double sharePrice = restTemplate.getForObject(SHARE_SERVICE_URL + "/" + shareCode + "/price", Double.class);

    
        Double totalCost = sharePrice * quantity;

        
        if (userBalance < totalCost) {
            return "Insufficient balance!";
        }

        
//        restTemplate.put(USER_SERVICE_URL + "/" + userId + "/deduct-balance?amount=" + totalCost, null);

        restTemplate.put(HOLDING_SERVICE_URL + "/buy?userId=" + userId + "&shareCode=" + shareCode + "&quantity=" + quantity + "&price=" + sharePrice, null);

        return "Shares purchased successfully!";
    }

    public String sell(Integer userId, String shareCode, Integer quantity) {

        Integer availableShares = restTemplate.getForObject(HOLDING_SERVICE_URL + "/" + userId + "/" + shareCode, Integer.class);

        if (availableShares < quantity) {
            return "Not enough shares to sell!";
        }

        Double sharePrice = restTemplate.getForObject(SHARE_SERVICE_URL + "/" + shareCode + "/price", Double.class);
        Double totalSaleValue = sharePrice * quantity;

        
//        restTemplate.put(USER_SERVICE_URL + "/" + userId + "/add-balance?amount=" + totalSaleValue, null);

    
        restTemplate.put(HOLDING_SERVICE_URL + "/sell?userId=" + userId + "&shareCode=" + shareCode + "&quantity=" + quantity + "&price=" + sharePrice, null);

        return "Shares sold successfully!";
    }
}
