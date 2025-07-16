package com.daoud.orderservice.inventory;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(prefix = "features", name = "kafka-enabled", havingValue = "true", matchIfMissing = false)
@Slf4j
@Component
public class InventoryStatusEventConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(
            topics = "${kafka.inventory-status-topic}",
            groupId = "${kafka.group-id}",
            containerFactory = "inventoryKafkaListenerContainerFactory"
    )
    public void consume(String rawMessage) {
        try {
            InventoryStatusEvent event = objectMapper.readValue(rawMessage, InventoryStatusEvent.class);
            log.info("📥 Received InventoryStatusEvent: {}", event);

            if (event.getStatus() == InventoryStatusEvent.Status.SUCCESS) {
                log.info("✅ Inventory confirmed for product: {}", event.getProductId());
            } else {
                log.warn("❌ Inventory insufficient for product: {}", event.getProductId());
            }
        } catch (Exception e) {
            log.error("❌ Failed to process InventoryStatusEvent", e);
        }
    }
}
