package com.daoud.inventoryservice.config;


import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;


@Configuration
public class OrderKafkaConsumerConfig extends KafkaConsumerConfig<String, String> {

    
    public OrderKafkaConsumerConfig(KafkaConsumerProperties consumerProps) {
        super(consumerProps);
    }

    @Override
    protected Class<StringDeserializer> getKeyDeserializer() {
        return StringDeserializer.class;
    }

    @Override
    protected Class<StringDeserializer> getValueDeserializer() {
        return StringDeserializer.class;
    }
    
    @Bean(name = "orderKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        return createKafkaListenerContainerFactory();
    }
}

