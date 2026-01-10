package com.beehive.authservice.consumer;

import com.beehive.authservice.service.AuthService;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserConsumer {
    private final AuthService service;

    public UserConsumer(AuthService service) {
        this.service = service;
    }

    @KafkaListener(
            topics = "${kafka.topic.user-registration-failed}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeUserCreated(String uid) throws FirebaseAuthException {
        service.deleteFirebaseUser(uid);
    }
}
