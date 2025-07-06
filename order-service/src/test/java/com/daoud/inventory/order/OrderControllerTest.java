package com.daoud.inventory.order;


import com.daoud.inventory.domain.OrderRequest;
import com.daoud.inventory.kafka.KafkaProducerProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldSendOrderToKafkaTopic() throws Exception {
        OrderRequest order = new OrderRequest("macbook-air", 2);
        String json = objectMapper.writeValueAsString(order);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)) 
                .andExpect(status().isCreated());

        verify(kafkaTemplate).send(eq("order-events"), contains("macbook-air"));
    }

    @TestConfiguration
    static class MockBeans {
        @Bean
        KafkaTemplate<String, String> kafkaTemplate() {
            return mock(KafkaTemplate.class);
        }

        @Bean
        KafkaProducerProperties kafkaProducerProperties() {
            return new KafkaProducerProperties(); 
        }
    }

}
