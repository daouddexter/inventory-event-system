package com.daoud.inventoryservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka")
@Data
public class KafkaConsumerProperties {
    private String bootstrapServers;
    private String groupId;
    private String orderTopic;
}
