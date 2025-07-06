package com.daoud.inventory.kafka.order;

import com.daoud.inventory.kafka.KafkaProducerConfig;
import com.daoud.inventory.kafka.KafkaProducerProperties;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderKafkaProducer extends KafkaProducerConfig<String,String> {
    


    public OrderKafkaProducer(KafkaProducerProperties props) {
        super(props);
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
