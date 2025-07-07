package com.daoud.inventoryservice.consumer;

import com.daoud.inventoryservice.config.KafkaConsumerProperties;
import com.daoud.inventoryservice.inventory.InventoryService;
import com.daoud.inventoryservice.order.OrderEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@EnableConfigurationProperties(KafkaConsumerProperties.class)
public class OrderEventConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final InventoryService inventoryService;

    public OrderEventConsumer(InventoryService inventoryService) {
        this.inventoryService = inventoryService;

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
        } else {
            log.warn("⚠️ Not enough stock for product {}", event.getProductId());
        }
    }
}
