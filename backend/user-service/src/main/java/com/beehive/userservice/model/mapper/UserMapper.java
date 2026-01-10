package com.beehive.userservice.model.mapper;

import com.beehive.userservice.model.entity.User;
import com.beehive.userservice.model.event.CreateUserEvent;
import com.beehive.userservice.model.response.CreateUserResponse;

public class UserMapper {
    private UserMapper() {
        throw new UnsupportedOperationException("This class should never be instantiated");
    }

    public static CreateUserResponse toCreateUserResponse(User user) {
        return null;
    }

    public static User toUserEntityFromEvent(CreateUserEvent event) {
        User user = new User();
        user.setId(event.uid());
        user.setEmail(event.email());
        user.setUsername(event.username());
        user.setAvatarURL(event.avatar());
        return user;
    }
}
