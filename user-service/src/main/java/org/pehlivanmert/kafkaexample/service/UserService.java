package org.pehlivanmert.kafkaexample.service;

import lombok.RequiredArgsConstructor;
import org.pehlivanmert.kafkaexample.config.kafka.producer.KafkaProducer;
import org.pehlivanmert.kafkaexample.config.kafka.properties.UserCreatedTopicProperties;
import org.pehlivanmert.kafkaexample.dto.UserCreateRequest;
import org.pehlivanmert.kafkaexample.dto.UserCreatedPayload;
import org.pehlivanmert.kafkaexample.entity.User;
import org.pehlivanmert.kafkaexample.repository.UserRepository;
import org.springframework.kafka.support.KafkaHeaders; // Make sure this explicit import is here
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// Remove or comment out this static import if you are using KafkaHeaders. directly
// import static org.springframework.kafka.support.KafkaHeaders.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final KafkaProducer kafkaProducer;
    private final UserCreatedTopicProperties userCreatedTopicProperties;

    public User create(UserCreateRequest userCreateRequest) {
        User user = User.getUser(userCreateRequest);
        User savedUser = userRepository.save(user);
        UserCreatedPayload payload = UserCreatedPayload.GetUserCreatedPayload(savedUser, userCreateRequest.getAddressText());

        Map<String, Object> headers = new HashMap<>();

        // Topic adını string olarak ekle
        headers.put(KafkaHeaders.TOPIC, userCreatedTopicProperties.getTopicName());

        // Key'i byte array olarak ekle - Spring Kafka 3.x gerekliliği
        headers.put(KafkaHeaders.KEY, savedUser.getId().toString().getBytes(StandardCharsets.UTF_8));

        kafkaProducer.sendMessage(headers, payload);
        return savedUser;
    }

    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }
}