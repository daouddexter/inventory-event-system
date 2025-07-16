package com.daoud.inventoryservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryController {

    @GetMapping("/api/ping")
    public String ping() {
        return "pong from inventory";
    }
}

