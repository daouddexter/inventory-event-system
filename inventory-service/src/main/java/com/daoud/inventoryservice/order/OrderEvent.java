package com.daoud.inventoryservice.order;

import lombok.Data;

@Data
public class OrderEvent {
    private String productId;
    private int quantity;
    
}
