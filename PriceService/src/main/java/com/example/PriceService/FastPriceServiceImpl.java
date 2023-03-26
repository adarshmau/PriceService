package com.example.PriceService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FastPriceServiceImpl implements FastPriceService {

    private final RestTemplate restTemplate;

    @Autowired
    public FastPriceServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.restTemplate.setRequestFactory(new SimpleClientHttpRequestFactory());
        ((SimpleClientHttpRequestFactory) this.restTemplate.getRequestFactory()).setConnectTimeout(30000);
        ((SimpleClientHttpRequestFactory) this.restTemplate.getRequestFactory()).setReadTimeout(30000);
    }

    @Override
    public int getFastestPrice(String product) {
        int amazonPrice = getPriceFromAmazon(product);
        int flipkartPrice = getPriceFromFlipkart(product);

        return Math.min(amazonPrice, flipkartPrice);
    }

    private int getPriceFromAmazon(String product) {
        long startTime = System.currentTimeMillis();
        String url = "https://price-amazon.free.beeceptor.com/service/" + product + "/price";
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(url, Map.class);
        long endTime = System.currentTimeMillis();
        long responseTime = endTime - startTime;
        int price = (int) responseEntity.getBody().get("price");
        System.out.println("Amazon response time for " + product + " is " + responseTime + "ms");
        return price;
    }

    private int getPriceFromFlipkart(String product) {
        long startTime = System.currentTimeMillis();
        String url = "https://price-flipkart.free.beeceptor.com/service/" + product + "/price";
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(url, Map.class);
        long endTime = System.currentTimeMillis();
        long responseTime = endTime - startTime;
        int price = (int) responseEntity.getBody().get("price");
        System.out.println("Flipkart response time for " + product + " is " + responseTime + "ms");
        return price;
    }
}
