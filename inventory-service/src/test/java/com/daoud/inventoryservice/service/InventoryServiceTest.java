package com.daoud.inventoryservice.service;

import com.daoud.inventoryservice.inventory.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InventoryServiceTest {

    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        inventoryService = new InventoryService();
    }

    @Test
    void testInitializeAndGetStock() {
        inventoryService.initializeProduct("ipad", 10);
        assertEquals(10, inventoryService.getAvailableStock("ipad"));
    }

    @Test
    void testReduceStockSuccess() {
        inventoryService.initializeProduct("macbook", 5);
        assertTrue(inventoryService.reduceStock("macbook", 3));
        assertEquals(2, inventoryService.getAvailableStock("macbook"));
    }

    @Test
    void testReduceStockFailure() {
        inventoryService.initializeProduct("iphone", 2);
        assertFalse(inventoryService.reduceStock("iphone", 5));
    }

    @Test
    void testReduceStockNonexistentProduct() {
        assertFalse(inventoryService.reduceStock("unknown", 1));
    }
}
