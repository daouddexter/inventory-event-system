package com.daoud.inventoryservice.inventory;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InventoryService {

    // In-memory store: productId -> available quantity
    private final Map<String, Integer> inventory = new ConcurrentHashMap<>();

    public void initializeProduct(String productId, int initialQuantity) {
        inventory.putIfAbsent(productId, initialQuantity);
    }

    public boolean reduceStock(String productId, int quantity) {
        return inventory.computeIfPresent(productId, (id, availableQty) -> {
            if (availableQty >= quantity) {
                return availableQty - quantity;
            } else {
                return availableQty; // Not enough stock, unchanged
            }
        }) != null;
    }

    public int getAvailableStock(String productId) {
        return inventory.getOrDefault(productId, 0);
    }

    public Map<String, Integer> getAllInventory() {
        return inventory;
    }
}
