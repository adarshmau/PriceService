package com.example.PriceService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PriceService {

    @Autowired
    private PriceRepository priceRepository;

    public List<Price> getPricesForProduct(String product, int userId)
    {
        return (List<Price>) priceRepository.findByProductAndUserId(product, userId);
    }
    
    public Double getPriceForProduct(String product, int userId) 
    {
        Price price = (Price) priceRepository.findByProductAndUserId(product, userId);
        
        return price != null ? price.getPrice() : null;
    }
   
        private RestTemplate restTemplate;

        public PriceService(RestTemplate restTemplate) 
        {
            this.restTemplate = restTemplate;
        }

        public double getFastestPrice(String product) 
        {
            String url1 = "https://price-amazon.free.beeceptor.com/service/" + product + "/price";
            String url2 = "https://price-flipkart.free.beeceptor.com/service/" + product + "/price";
            double price1 = 0.0, price2 = 0.0;
            long startTime = System.currentTimeMillis();
            ResponseEntity<Map> response1 = restTemplate.getForEntity(url1, Map.class);
            long endTime = System.currentTimeMillis();
            long duration1 = endTime - startTime;
            startTime = System.currentTimeMillis();
            ResponseEntity<Map> response2 = restTemplate.getForEntity(url2, Map.class);
            endTime = System.currentTimeMillis();
            long duration2 = endTime - startTime;
            if (duration1 < duration2) {
                price1 = Double.parseDouble(response1.getBody().get("price").toString());
                return price1;
            } else {
                price2 = Double.parseDouble(response2.getBody().get("price").toString());
                return price2;
            }
        }
    

}
