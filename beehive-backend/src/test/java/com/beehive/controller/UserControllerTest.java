package com.beehive.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.beehive.dto.UserDto;
import com.beehive.entity.UserEntity;
import com.beehive.response.UserResponse;
import com.beehive.service.UserService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  @Mock UserService userService;
  @InjectMocks UserController userController;

  private UserResponse userResponseSuccess = new UserResponse();
  private UserResponse userResponseFailure = new UserResponse(null, "FAILURE");

  @BeforeEach
  void setUp() {
    UserEntity user1 = new UserEntity();
    user1.setEmail("user1@example.com");
    user1.setUsername("user1");
    user1.setPassword("password1");

    UserEntity user2 = new UserEntity();
    user2.setEmail("user2@example.com");
    user2.setUsername("user2");
    user2.setPassword("password2");

    List<UserDto> userDtoList =
        List.of(
            new UserDto("user1", "user1@example.com", "password1", false),
            new UserDto("user2", "user2@example.com", "password2", false));

    userResponseSuccess.setUsers(userDtoList);
    userResponseSuccess.setStatusMessage("SUCCESS");
  }

  @Test
  void getAllUsersSuccess() {
    when(userService.serviceUserGetAll()).thenReturn(userResponseSuccess);
    UserResponse response = userController.getAllUsers();

    assertNotNull(response);
    assertNotNull(response.getUsers());
    assertEquals("SUCCESS", response.getStatusMessage());
    assertEquals(2, response.getUsers().size());

    UserDto userDto1 = response.getUsers().get(0);
    assertEquals("user1@example.com", userDto1.getEmail());
    assertEquals("user1", userDto1.getUsername());
    assertEquals("password1", userDto1.getPassword());

    UserDto userDto2 = response.getUsers().get(1);
    assertEquals("user2@example.com", userDto2.getEmail());
    assertEquals("user2", userDto2.getUsername());
    assertEquals("password2", userDto2.getPassword());

    verify(userService, times(1)).serviceUserGetAll();
  }

  @Test
  void getAllUsersFailure() {
    when(userService.serviceUserGetAll()).thenReturn(userResponseFailure);
    UserResponse response = userController.getAllUsers();

    assertNotNull(response);
    assertNull(response.getUsers());
    assertEquals("FAILURE", response.getStatusMessage());

    verify(userService, times(1)).serviceUserGetAll();
  }

  @Test
  void register() {}

  @Test
  void forgetPassword() {}

  @Test
  void searchUsers() {}

  @Test
  void checkUserExists() {}
}
