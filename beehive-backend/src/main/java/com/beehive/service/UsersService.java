package com.beehive.service;

import com.beehive.request.UserRequest;
import com.beehive.response.UserResponse;

public interface UsersService {

    UserResponse getAllUsers();

    UserResponse register(UserRequest request);

    UserResponse forgetPassword(UserRequest request);

    UserResponse searchUsers(String username);
}
