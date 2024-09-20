package com.example.knu_mingle.controller;

import com.example.knu_mingle.domain.Market;
import com.example.knu_mingle.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/market")
public class MarketRestController {

    @Autowired
    private MarketService marketService;

    @PostMapping
    public ResponseEntity<Market> createMarket(@RequestBody Market market) {
        Market createdMarket = marketService.createMarket(market);
        return ResponseEntity.status(201).body(createdMarket);
    }

}
