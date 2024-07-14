package com.beehive.controller;

import com.beehive.request.UserRequest;
import com.beehive.response.UserResponse;
import com.beehive.service.UserService;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1.0/user")
public class UserController {

  private final Logger logger = LoggerFactory.getLogger(UserController.class);

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(path = "/all")
  public UserResponse getAllUsers() {
    UserResponse response = userService.serviceUserGetAll();
    logger.info("Users Controller" + " in getAllUsers() call " + response.getStatusMessage());
    return response;
  }

  @PostMapping(path = "/register")
  public UserResponse register(@RequestBody UserRequest request) {
    UserResponse response = userService.serviceUserRegister(request);
    logger.info("Users Controller" + " in register() call " + response.getStatusMessage());
    return response;
  }

  @PostMapping(path = "/forgot-password")
  public UserResponse forgetPassword(@RequestBody UserRequest request) {
    UserResponse response = userService.serviceUserForgotPassword(request);
    logger.info("Users Controller" + " in forgetPassword() call " + response.getStatusMessage());
    return response;
  }

  @GetMapping(path = "/search/{username}")
  public UserResponse searchUsers(@PathVariable(name = "username") String username) {
    UserResponse response = userService.serviceUserSearch(username);
    logger.info("Users Controller" + " in searchUsers() call " + response.getStatusMessage());
    return response;
  }

  @GetMapping(path = "/check/{username}")
  public ResponseEntity<Map<String, Boolean>> checkUserExists(
      @PathVariable(name = "username") String username) {
    Map<String, Boolean> response = new HashMap<String, Boolean>(1);
    response.put("doesUserExist", userService.serviceUserCheckIfExists(username));
    logger.info("Users Controller" + " in checkUserExists() call " + response);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
