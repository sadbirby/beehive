package com.beehive.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.beehive.dto.UserDto;
import com.beehive.entity.UserEntity;
import com.beehive.request.UserRequest;
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

  private final UserRequest userRequest = new UserRequest();
  private final UserResponse userResponseSuccess = new UserResponse();
  private final UserResponse userResponseFailure = new UserResponse(null, "FAILURE");
  @Mock UserService userService;
  @InjectMocks UserController userController;

  @BeforeEach
  void setUp() {
    UserEntity user1 = new UserEntity("user1", "user1@example.com", "P@ssw0rd1");
    UserEntity user2 = new UserEntity("user2", "user2@example.com", "P@ssw0rd2");

    List<UserDto> userDtoList =
        List.of(
            new UserDto("user1", "user1@example.com", "P@ssw0rd1", false),
            new UserDto("user2", "user2@example.com", "P@ssw0rd2", false));

    userRequest.setUser(new UserDto("user", "user@example.com", "P@ssw0rd", false));
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
    assertEquals("P@ssw0rd1", userDto1.getPassword());

    UserDto userDto2 = response.getUsers().get(1);
    assertEquals("user2@example.com", userDto2.getEmail());
    assertEquals("user2", userDto2.getUsername());
    assertEquals("P@ssw0rd2", userDto2.getPassword());

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
  void registerSuccess() {
    when(userService.serviceUserRegister(userRequest))
        .thenReturn(new UserResponse(null, "SUCCESS"));
    UserResponse response = userService.serviceUserRegister(userRequest);

    assertNotNull(response);
    assertNull(response.getUsers());
    assertEquals("SUCCESS", response.getStatusMessage());

    verify(userService, times(1)).serviceUserRegister(userRequest);
  }

  @Test
  void registerFailure() {
    when(userService.serviceUserRegister(userRequest)).thenReturn(userResponseFailure);
    UserResponse response = userService.serviceUserRegister(userRequest);

    assertNotNull(response);
    assertNull(response.getUsers());
    assertEquals("FAILURE", response.getStatusMessage());

    verify(userService, times(1)).serviceUserRegister(userRequest);
  }

  @Test
  void forgetPassword() {}

  @Test
  void searchUsers() {}

  @Test
  void checkUserExists() {}
}
