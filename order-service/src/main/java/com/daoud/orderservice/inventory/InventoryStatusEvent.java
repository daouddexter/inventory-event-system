package com.daoud.orderservice.inventory;


import lombok.Data;

@Data
public class InventoryStatusEvent {
    private String productId;
    private int availableStock;
    private Status status;

    public enum Status {
        SUCCESS,
        FAILED
    }
}
