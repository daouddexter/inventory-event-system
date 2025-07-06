package com.daoud.inventory.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public abstract class KafkaProducerConfig<K, V> {
    private final KafkaProducerProperties props;

    @Autowired
    public KafkaProducerConfig(KafkaProducerProperties props) {
        this.props = props;
    }

    @Bean
    public ProducerFactory<K, V> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, props.getBootstrapServers());
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, getKeySerializer());
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, getValueSerializer());
        config.put(ProducerConfig.ACKS_CONFIG, props.getProducer().getAcks());
        config.put(ProducerConfig.RETRIES_CONFIG, props.getProducer().getRetries());
        config.put(ProducerConfig.LINGER_MS_CONFIG, props.getProducer().getLingerMs());
        config.put(ProducerConfig.BATCH_SIZE_CONFIG, props.getProducer().getBatchSize());
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<K, V> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    protected abstract Class<? extends Serializer<K>> getKeySerializer();

    protected abstract Class<? extends Serializer<V>> getValueSerializer();
}
