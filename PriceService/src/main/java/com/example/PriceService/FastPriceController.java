package com.example.PriceService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FastPriceController {

    private final FastPriceService fastPriceService;

    @Autowired
    public FastPriceController(FastPriceService fastPriceService) {
        this.fastPriceService = fastPriceService;
    }

    @GetMapping("/price/{product}")
    public ResponseEntity<Map<String, Integer>> getPrice(@PathVariable String product) {
        int price = fastPriceService.getFastestPrice(product);

        Map<String, Integer> response = new HashMap<>();
        response.put("price", price);

        return ResponseEntity.ok(response);
    }
}
