package com.beehive.web.request;

import com.beehive.domain.dto.UserDto;

public class UserRequest {

    UserDto user;

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
