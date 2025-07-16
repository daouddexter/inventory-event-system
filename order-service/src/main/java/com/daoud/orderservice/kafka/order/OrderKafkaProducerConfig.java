package com.daoud.orderservice.kafka.order;


import com.daoud.core.KafkaProducerConfig;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class OrderKafkaProducerConfig extends KafkaProducerConfig<String, String> {


    public OrderKafkaProducerConfig(@Value("${kafka.bootstrap-servers}") String bootstrapServer,
                                    @Value("${kafka.producer.acks}") String acks,
                                    @Value("${kafka.producer.retries}") Integer retries,
                                    @Value("${kafka.producer.linger-ms}") Integer lingerMs,
                                    @Value("${kafka.producer.batch-size}") Integer batchSize) {
       super(bootstrapServer,acks, retries, lingerMs, batchSize);
    }

    @Bean("OrderKafkaTemplate")
    @Override
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Override
    protected Class<? extends Serializer<String>> getKeySerializer() {
        return StringSerializer.class;
    }

    @Override
    protected Class<? extends Serializer<String>> getValueSerializer() {
        return StringSerializer.class;
    }
}
