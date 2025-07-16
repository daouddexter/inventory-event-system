package com.daoud.orderservice.controller;

import com.daoud.orderservice.domain.OrderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);


    private final KafkaTemplate<String, String> kafkaTemplate;


    private final String orderTopic;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public OrderController(@Qualifier("OrderKafkaTemplate") KafkaTemplate<String, String> kafkaTemplate,
                           @Value("${kafka.order-topic}") String orderTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.orderTopic = orderTopic;
    }

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest order) {
        try {
            String message = objectMapper.writeValueAsString(order);
            kafkaTemplate.send(orderTopic, message);

            return ResponseEntity.status(HttpStatus.CREATED).body("Success");
        } catch (Exception e) {
            log.error("Error sending kafka message ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed");
        }
    }
}
