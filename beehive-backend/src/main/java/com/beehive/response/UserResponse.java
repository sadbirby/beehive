package com.beehive.response;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.beehive.dto.UserDto;

public class UserResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 5432683200958075030L;

    private List<UserDto> users;
    private String statusMessage;

    public UserResponse() {
    }

    public UserResponse(List<UserDto> users, String statusMessage) {
        this.users = users;
        this.statusMessage = statusMessage;
    }

    public List<UserDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
