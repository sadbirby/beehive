package com.beehive.userservice.model.event;

public record CreateUserEvent (
        String uid,
        String email,
        String username,
        String avatar
) {
}
