package com.beehive.userservice.model.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public record FollowId(
        String followerId,
        String followeeId
) {
}
