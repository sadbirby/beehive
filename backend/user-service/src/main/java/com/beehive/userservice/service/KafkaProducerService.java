package com.beehive.userservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String TOPIC_USER_REGISTRATION_FAILED;

    public KafkaProducerService(
            KafkaTemplate<String, String> kafkaTemplate,
            @Value("${kafka.topic.user-registration-failed}") String TOPIC_USER_REGISTRATION_FAILED
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.TOPIC_USER_REGISTRATION_FAILED = TOPIC_USER_REGISTRATION_FAILED;
    }

    public void produceUserRegistrationFailedEvent(String uid) {
        kafkaTemplate.send(TOPIC_USER_REGISTRATION_FAILED, uid);
    }
}
