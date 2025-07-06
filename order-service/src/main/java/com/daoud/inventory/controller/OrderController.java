package com.daoud.inventory.controller;

import com.daoud.inventory.domain.OrderRequest;
import com.daoud.inventory.kafka.KafkaProducerProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaProducerProperties kafkaProps;
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest order){
        try {
            String message = objectMapper.writeValueAsString(order);
            String topic = kafkaProps.getTopics().getOrderEvents();
            kafkaTemplate.send(topic, message);
            
            return ResponseEntity.status(HttpStatus.CREATED).body("Success");
        } catch (Exception e) {
            log.error("Error sending kafka message ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed");
        }
    }
}
