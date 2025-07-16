package com.daoud.inventoryservice.inventory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InventoryStatusEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${kafka.inventory-status-topic}")
    private String inventoryStatusTopic;

    public InventoryStatusEventProducer(@Qualifier("InventoryStatusKafkaTemplate") KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(InventoryStatusEvent event) {
        try {
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(inventoryStatusTopic, json);
            log.info("📤 Sent InventoryStatusEvent: {}", json);
        } catch (JsonProcessingException e) {
            log.error("❌ Error serializing InventoryStatusEvent", e);
        }
    }
}
