//package com.example.demo.schedulers;
//
//import com.example.demo.services.SharePriceService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//public class PriceUpdateScheduler {
//
//    @Autowired
//    private SharePriceService sharePriceService;
//
//    @Scheduled(fixedRate = 5000) // Every 5 seconds
//    public void updatePrices() {
//        sharePriceService.fetchAndUpdateShares();
//    }
//}
