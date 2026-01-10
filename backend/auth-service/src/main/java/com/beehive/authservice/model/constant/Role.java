package com.beehive.authservice.model.constant;

import com.google.api.client.util.Value;

public enum Role {
    @Value("admin") ADMIN,
    @Value("moderator") MODERATOR,
    @Value("user") USER,
}
