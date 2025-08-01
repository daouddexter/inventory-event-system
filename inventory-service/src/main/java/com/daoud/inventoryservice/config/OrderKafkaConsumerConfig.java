package com.daoud.inventoryservice.config;


import com.daoud.core.KafkaConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;


@Configuration
public class OrderKafkaConsumerConfig extends KafkaConsumerConfig<String, String> {


    public OrderKafkaConsumerConfig(@Value("${kafka.bootstrap-servers}") String bootstrapServers,
                                    @Value("${kafka.group-id}") String groupId) {
        super(bootstrapServers, groupId);
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

