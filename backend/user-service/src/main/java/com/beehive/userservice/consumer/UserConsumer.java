package com.beehive.userservice.consumer;

import com.beehive.userservice.model.event.CreateUserEvent;
import com.beehive.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserConsumer {
    private final UserService service;

    @KafkaListener(
            topics = "${kafka.topic.user-identity-created}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeUserIdentityCreated(CreateUserEvent event) {
        service.create(event);
    }


}
