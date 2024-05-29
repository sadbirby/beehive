package com.beehive.response;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.beehive.dto.UsersDto;

public class UserResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 5432683200958075030L;

    private List<UsersDto> usersDto;
    private String statusMessage;

    public List<UsersDto> getUsersDto() {
        return usersDto;
    }

    public void setUsersDto(List<UsersDto> usersDto) {
        this.usersDto = usersDto;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
