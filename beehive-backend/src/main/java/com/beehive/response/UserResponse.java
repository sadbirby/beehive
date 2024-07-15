package com.beehive.response;

import com.beehive.dto.UserDto;
import java.util.List;

public class UserResponse {

  private List<UserDto> users;
  private String statusMessage;

  public UserResponse() {}

  public UserResponse(List<UserDto> users, String statusMessage) {
    this.users = users;
    this.statusMessage = statusMessage;
  }

  public List<UserDto> getUsers() {
    return users;
  }

  public void setUsers(List<UserDto> users) {
    this.users = users;
  }

  public String getStatusMessage() {
    return statusMessage;
  }

  public void setStatusMessage(String statusMessage) {
    this.statusMessage = statusMessage;
  }
}
