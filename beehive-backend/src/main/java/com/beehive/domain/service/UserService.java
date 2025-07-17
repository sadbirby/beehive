package com.beehive.domain.service;

import com.beehive.web.request.UserRequest;
import com.beehive.web.response.UserResponse;

public interface UserService {

    Boolean serviceUserCheckIfExists(String username);

    UserResponse serviceUserGetAll();

    UserResponse serviceUserRegister(UserRequest request);

    UserResponse serviceUserForgotPassword(UserRequest request);

    UserResponse serviceUserSearch(String username);
}
