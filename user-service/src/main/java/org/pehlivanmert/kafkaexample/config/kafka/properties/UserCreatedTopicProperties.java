package org.pehlivanmert.kafkaexample.config.kafka.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka.topic.user-created")
@Getter
@Setter
public class UserCreatedTopicProperties {
    private String topicName;
    private int partitionCount;
    private short replicationFactor;
    private String retentionInMs;
}
