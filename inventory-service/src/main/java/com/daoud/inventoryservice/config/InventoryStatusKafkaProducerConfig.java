package com.daoud.inventoryservice.config;

import com.daoud.core.KafkaProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class InventoryStatusKafkaProducerConfig extends KafkaProducerConfig<String, String> {


    public InventoryStatusKafkaProducerConfig(@Value("${kafka.bootstrap-servers}") String bootstrapServer,
                                    @Value("${kafka.producer.acks}") String acks,
                                    @Value("${kafka.producer.retries}") Integer retries,
                                    @Value("${kafka.producer.linger-ms}") Integer lingerMs,
                                    @Value("${kafka.producer.batch-size}") Integer batchSize) {
        super(bootstrapServer,acks, retries, lingerMs, batchSize);
    }

    @Bean("InventoryStatusKafkaTemplate")
    @Override
    public KafkaTemplate<String, String> kafkaTemplate() {
       return new KafkaTemplate<>(producerFactory());
    }

    @Override
    protected Class<? extends org.apache.kafka.common.serialization.Serializer<String>> getKeySerializer() {
        return StringSerializer.class;
    }

    @Override
    protected Class<? extends org.apache.kafka.common.serialization.Serializer<String>> getValueSerializer() {
        return StringSerializer.class;
    }
}
