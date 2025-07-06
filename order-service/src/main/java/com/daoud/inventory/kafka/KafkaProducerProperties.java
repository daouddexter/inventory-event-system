package com.daoud.inventory.kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "kafka")
public class KafkaProducerProperties {

    private String bootstrapServers;
    private Producer producer = new Producer();
    private Topics topics = new Topics();

    @Data
    public static class Producer {
        private String acks;
        private int retries;
        private int lingerMs;
        private int batchSize;
    }

    @Data
    public static class Topics {
        private String orderEvents;
    }
}
