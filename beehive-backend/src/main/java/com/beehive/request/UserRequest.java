package com.beehive.request;

import java.io.Serial;
import java.io.Serializable;

import com.beehive.dto.UserDto;

public class UserRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -8300957195016445421L;

    UserDto user;

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
