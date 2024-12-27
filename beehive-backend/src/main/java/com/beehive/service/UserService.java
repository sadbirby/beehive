package com.beehive.service;

import com.beehive.request.UserRequest;
import com.beehive.response.UserResponse;

public interface UserService {

    Boolean serviceUserCheckIfExists(String username);

    UserResponse serviceUserGetAll();

    UserResponse serviceUserRegister(UserRequest request);

    UserResponse serviceUserForgotPassword(UserRequest request);

    UserResponse serviceUserSearch(String username);
}
