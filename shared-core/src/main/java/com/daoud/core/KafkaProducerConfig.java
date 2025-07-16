package com.daoud.core;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

public abstract class KafkaProducerConfig<K, V> {
    private final String bootstrapServer;
    private final String acks;
    private final Integer retries;
    private final Integer lingerMs;
    private final Integer batchSize;

    public KafkaProducerConfig(String bootstrapServer, String acks,
                               Integer retries, Integer lingerMs, Integer batchSize) {
        this.bootstrapServer = bootstrapServer;
        this.acks = acks;
        this.retries = retries;
        this.lingerMs = lingerMs;
        this.batchSize = batchSize;
    }


    protected ProducerFactory<K, V> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, getKeySerializer());
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, getValueSerializer());
        config.put(ProducerConfig.ACKS_CONFIG, acks);
        config.put(ProducerConfig.RETRIES_CONFIG, retries);
        config.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs);
        config.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        return new DefaultKafkaProducerFactory<>(config);
    }


    public abstract KafkaTemplate<K, V> kafkaTemplate();

    protected abstract Class<? extends Serializer<K>> getKeySerializer();

    protected abstract Class<? extends Serializer<V>> getValueSerializer();
}
