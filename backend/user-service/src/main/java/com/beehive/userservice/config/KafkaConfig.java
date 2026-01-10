package com.beehive.userservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    private final String userIdentityCreatedTopic;

    public KafkaConfig(
            @Value("${kafka.topic.user-identity-created}") String userIdentityCreatedTopic
    ) {
        this.userIdentityCreatedTopic = userIdentityCreatedTopic;
    }

    @Bean
    public NewTopic userIdentityCreatedTopic() {
        return TopicBuilder.name(userIdentityCreatedTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
