package com.daoud.core;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

public abstract class KafkaConsumerConfig<K, V> {

    private final String bootstrapServers;
    private final String groupId;

    protected KafkaConsumerConfig(String bootstrapServers, String groupId) {
        this.bootstrapServers = bootstrapServers;
        this.groupId = groupId;
    }


    protected abstract Class<? extends Deserializer<K>> getKeyDeserializer();

    protected abstract Class<? extends Deserializer<V>> getValueDeserializer();
    

    public ConsumerFactory<K, V> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, getKeyDeserializer());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, getValueDeserializer());
        return new DefaultKafkaConsumerFactory<>(props);
    }

    protected ConcurrentKafkaListenerContainerFactory<K, V> createKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<K, V>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
    
    public abstract ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory();
    
}
