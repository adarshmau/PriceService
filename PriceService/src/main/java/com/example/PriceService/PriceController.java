package com.example.PriceService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class PriceController {

    @Autowired
    private PriceService priceService;
    
    
    @GetMapping("/{product}/price")
    public ResponseEntity<List<Price>> getPricesForProduct(@PathVariable String product, 
    		@RequestHeader("X-User") int userId) {
    	
        List<Price> prices = priceService.getPricesForProduct(product, userId);
        return ResponseEntity.ok(prices);
    }
    
    @GetMapping("/{product}/price")
    public ResponseEntity<Map<String, Double>> getPricesForProduct1(@PathVariable String product, 
    		@RequestHeader("X-User") int userId) {
        Double price = priceService.getPriceForProduct(product, userId);
        Map<String, Double> response = new HashMap<>();
        response.put("price", price);
        return ResponseEntity.ok(response);
    }
}
