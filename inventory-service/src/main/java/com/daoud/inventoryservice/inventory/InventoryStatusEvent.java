package com.daoud.inventoryservice.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryStatusEvent {
    private String productId;
    private int availableStock;
    private Status status;


    public enum Status {
        SUCCESS,
        FAILED
    }
}
