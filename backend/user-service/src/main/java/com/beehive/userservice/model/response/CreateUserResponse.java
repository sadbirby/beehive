package com.beehive.userservice.model.response;

import com.beehive.userservice.model.constant.AccountStatus;
import com.beehive.userservice.model.constant.ProfileCategory;

import java.time.LocalDateTime;

public record CreateUserResponse(
        String id,
        String username,
        String email,
        AccountStatus accountStatus,
        ProfileCategory profileCategory,
        LocalDateTime createdDate,
        Integer version
) {
}