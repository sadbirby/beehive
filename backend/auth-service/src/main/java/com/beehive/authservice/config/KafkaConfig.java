package com.beehive.authservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    private final String userRegistrationFailedTopic;

    public KafkaConfig(
            @Value("${kafka.topic.user-registration-failed}") String userRegistrationFailedTopic
    ) {
        this.userRegistrationFailedTopic = userRegistrationFailedTopic;
    }

    @Bean
    public NewTopic userRegistrationFailedTopic() {
        return TopicBuilder.name(userRegistrationFailedTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
