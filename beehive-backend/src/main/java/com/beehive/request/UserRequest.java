package com.beehive.request;

import java.io.Serial;
import java.io.Serializable;

import com.beehive.dto.UsersDto;

public class UserRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -8300957195016445421L;

    UsersDto usersDTO;

    public UsersDto getUsersDTO() {
        return usersDTO;
    }

    public void setUsersDTO(UsersDto usersDTO) {
        this.usersDTO = usersDTO;
    }
}
