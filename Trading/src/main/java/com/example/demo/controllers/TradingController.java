package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.services.TradingService;

@RestController
@RequestMapping("/trade")
public class TradingController {

    @Autowired
    TradingService ts;

    @PostMapping("/buy")
    public String buyShares(@RequestParam Integer userId, @RequestParam String shareCode, @RequestParam Integer quantity) {
        return ts.buy(userId, shareCode, quantity);
    }

    @PostMapping("/sell")
    public String sellShares(@RequestParam Integer userId, @RequestParam String shareCode, @RequestParam Integer quantity) {
        return ts.sell(userId, shareCode, quantity);
    }
}
