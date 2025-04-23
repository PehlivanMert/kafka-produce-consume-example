package org.pehlivanmert.kafkaexample.config.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * KafkaProducer sınıfı, Kafka'ya mesaj göndermekten sorumlu bileşendir.
 * KafkaTemplate kullanarak verilen mesajı belirlenen topic'e gönderir
 * ve gönderim sonucu hakkında log kaydı oluşturur.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    /**
     * Kafka ile etkileşim kurmak için kullanılan, Spring tarafından sağlanan yardımcı sınıftır.
     * Bu template aracılığıyla Kafka'ya mesajlar gönderilir.
     */
    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Verilen mesajı Kafka'ya gönderen metottur. Gönderim işlemi tamamlandığında,
     * başarılı ya da başarısız olduğuna dair log kaydı tutulur.
     *
     * @param payload Kafka'ya gönderilecek olan mesaj nesnesidir.
     *                Spring Messaging'in GenericMessage sınıfı ile sarılmıştır.
     */
    public void sendMessage(Map<String, Object> headers, Object payload) {
        // GenericMessage oluşturma
        GenericMessage<Object> message = new GenericMessage<>(payload, headers);

        try {
            // Mesajı gönderme
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(message);

            future.whenComplete((result, ex) -> {
                if (ex != null) {
                    log.error("Unable to deliver message to kafka: {}", ex.getMessage(), ex);
                } else {
                    if (Objects.isNull(result)) {
                        log.info("Empty result on success for message {}", payload);
                        return;
                    }
                    RecordMetadata metadata = result.getRecordMetadata();
                    log.info("Message published: topic={}, partition={}, offset={}, payload={}",
                            metadata.topic(), metadata.partition(), metadata.offset(), payload);
                }
            });
        } catch (Exception e) {
            log.error("Error sending message to Kafka: {}", e.getMessage(), e);
        }
    }
}