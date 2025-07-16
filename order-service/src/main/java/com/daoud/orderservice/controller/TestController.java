package com.daoud.orderservice.controller;

import com.daoud.orderservice.InventoryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final InventoryClient inventoryClient;

    public TestController(InventoryClient inventoryClient) {
        this.inventoryClient = inventoryClient;
    }

    @GetMapping("/api/test-inventory-call")
    public String testCall() {
        return inventoryClient.callInventoryPing();
    }
}

