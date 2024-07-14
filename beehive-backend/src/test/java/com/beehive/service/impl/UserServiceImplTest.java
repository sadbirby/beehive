package com.beehive.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.beehive.dto.UserDto;
import com.beehive.entity.UserEntity;
import com.beehive.repository.UserRepository;
import com.beehive.response.UserResponse;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock UserRepository userRepository;
  @InjectMocks UserServiceImpl userService;

  private List<UserEntity> userEntityList;

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

    userEntityList = Arrays.asList(user1, user2);
  }

  @Test
  void serviceUserCheckIfExistsTrue() {
    String username = "mock-user";
    when(userRepository.existsById(username)).thenReturn(true);
    Boolean result = userService.serviceUserCheckIfExists(username);
    assertTrue(result);
    verify(userRepository).existsById(username);
  }

  @Test
  void serviceUserCheckIfExistsFalse() {
    String username = "mock-user";
    when(userRepository.existsById(username)).thenReturn(false);
    Boolean result = userService.serviceUserCheckIfExists(username);
    assertFalse(result);
    verify(userRepository, times(1)).existsById(username);
  }

  @Test
  void serviceUserGetAllSuccess() {
    when(userRepository.findAll()).thenReturn(userEntityList);
    UserResponse response = userService.serviceUserGetAll();

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

    verify(userRepository, times(1)).findAll();
  }

  @Test
  void serviceUserGetAllFailure() {
    when(userRepository.findAll()).thenThrow(new RuntimeException("Database Connection Error"));
    UserResponse response = userService.serviceUserGetAll();

    assertNotNull(response);
    assertNull(response.getUsers());
    assertEquals("FAILURE", response.getStatusMessage());

    verify(userRepository, times(1)).findAll();
  }

  @Test
  void serviceUserRegister() {}

  @Test
  void serviceUserForgotPassword() {}

  @Test
  void serviceUserSearch() {}
}
