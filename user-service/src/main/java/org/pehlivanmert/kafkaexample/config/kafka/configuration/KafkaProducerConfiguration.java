package org.pehlivanmert.kafkaexample.config.kafka.configuration;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka üretici (producer) yapılandırmasını içeren sınıftır.
 * Kafka'ya veri göndermek için gerekli olan Producer ayarlarını sağlar.
 */
@Configuration
public class KafkaProducerConfiguration {

    // Kafka host bilgisini application.properties'den alın
    @Value("${kafka.host:localhost:9092}")
    private String kafkaBroker;

    @Bean
    public KafkaTemplate<String, Object> tyKafkaTemplate() {
        return new KafkaTemplate<>(producerConfig());
    }

    private ProducerFactory<String, Object> producerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBroker);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        // Ek yapılandırmalar
        config.put(ProducerConfig.ACKS_CONFIG, "all");
        config.put(ProducerConfig.RETRIES_CONFIG, 10);
        return new DefaultKafkaProducerFactory<>(config);
    }
}