package com.daoud.inventoryservice.consumer;

import com.daoud.inventoryservice.inventory.InventoryService;
import com.daoud.inventoryservice.inventory.InventoryStatusEvent;
import com.daoud.inventoryservice.inventory.InventoryStatusEventProducer;
import com.daoud.inventoryservice.order.OrderEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@ConditionalOnProperty(prefix = "features", name = "kafka-enabled", havingValue = "true", matchIfMissing = false)
@Slf4j
@Component
public class OrderEventConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final InventoryService inventoryService;
    private final InventoryStatusEventProducer inventoryStatusEventProducer;

    public OrderEventConsumer(InventoryService inventoryService, InventoryStatusEventProducer inventoryStatusEventProducer) {
        this.inventoryService = inventoryService;

        this.inventoryStatusEventProducer = inventoryStatusEventProducer;
    }

    @KafkaListener(
            topics = "${kafka.order-topic}",
            groupId = "${kafka.group-id}",
            containerFactory = "orderKafkaListenerContainerFactory"
    )
    public void consume(String message) throws IOException {
        OrderEvent event = objectMapper.readValue(message, OrderEvent.class);
        log.info("✅ Received OrderEvent: {}", event);

        inventoryService.initializeProduct(event.getProductId(), 50); // Default init
        boolean success = inventoryService.reduceStock(event.getProductId(), event.getQuantity());

        if (success) {
            log.info("✅ Inventory updated for product {}: new stock = {}",
                    event.getProductId(),
                    inventoryService.getAvailableStock(event.getProductId()));
            InventoryStatusEvent.Status status = success
                    ? InventoryStatusEvent.Status.SUCCESS
                    : InventoryStatusEvent.Status.FAILED;

            InventoryStatusEvent inventoryStatusEvent = new InventoryStatusEvent(
                    event.getProductId(),
                    inventoryService.getAvailableStock(event.getProductId()),
                    status
            );

            inventoryStatusEventProducer.send(inventoryStatusEvent);
        } else {
            log.warn("⚠️ Not enough stock for product {}", event.getProductId());
        }
    }
}
