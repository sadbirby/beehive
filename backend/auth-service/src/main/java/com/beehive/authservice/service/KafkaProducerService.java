package com.beehive.authservice.service;

import com.beehive.authservice.model.event.CreateUserEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);

    private final KafkaTemplate<String, CreateUserEvent> kafkaTemplate;
    private final String TOPIC_USER_IDENTITY_CREATED;

    public KafkaProducerService(
            KafkaTemplate<String, CreateUserEvent> kafkaTemplate,
            @Value("${kafka.topic.user-identity-created}") String topicCreateUser
    ) {
        this.kafkaTemplate = kafkaTemplate;
        TOPIC_USER_IDENTITY_CREATED = topicCreateUser;
    }

    public void produceUserIdentityCreatedEvent(CreateUserEvent event) {
        kafkaTemplate.send(TOPIC_USER_IDENTITY_CREATED, event);
        log.info("Event sent to topic {}: {}", TOPIC_USER_IDENTITY_CREATED, event);
    }
}
