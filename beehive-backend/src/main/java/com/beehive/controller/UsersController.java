package com.beehive.controller;

import com.beehive.request.UserRequest;
import com.beehive.response.UserResponse;
import com.beehive.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1.0/user")
@CrossOrigin("*")
public class UsersController {

    @Autowired
    UsersService usersService;

    private final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @GetMapping(path = "/all")
    public UserResponse getAllUsers() {
        UserResponse response = usersService.getAllUsers();
        logger.info("Users Controller" +" in getAllUsers() call " + response.getStatusMessage());
        return response;
    }

    @PostMapping(path = "/register")
    public UserResponse register(@RequestBody UserRequest request) {
        UserResponse response = usersService.register(request);
        logger.info("Users Controller" + " in register() call " + response.getStatusMessage());
        return response;
    }

    @PostMapping(path = "/forgot-password")
    public UserResponse forgetPassword(@RequestBody UserRequest request) {
        UserResponse response = usersService.forgetPassword(request);
        logger.info("Users Controller" + " in forgetPassword() call " + response.getStatusMessage());
        return response;
    }

    @GetMapping(path = "/search/{username}")
    public UserResponse searchUsers(@PathVariable(name = "username") String username) {
        UserResponse response = usersService.searchUsers(username);
        logger.info("Users Controller" + " in searchUsers() call " + response.getStatusMessage());
        return response;
    }
}
