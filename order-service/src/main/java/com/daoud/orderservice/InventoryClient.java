package com.daoud.orderservice;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InventoryClient {

    private final RestTemplate restTemplate;

    public InventoryClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String callInventoryPing() {
        // service name must match 'spring.application.name' of inventory-service
        return restTemplate.getForObject("http://inventory-service/api/ping", String.class);
    }
}

