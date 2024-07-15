package com.beehive.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserDto {

  @NotBlank
  @Pattern(
      regexp = "^[a-zA-Z0-9]{4,32}$",
      message = "username must be 4 to 32 characters long, containing no special characters")
  private String username;

  @NotBlank
  @Email(
      regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+.[aag-z]{2,3}",
      flags = Pattern.Flag.CASE_INSENSITIVE,
      message = "invalid email format")
  private String email;

  @NotBlank
  @Pattern(
      regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,32}$",
      message =
          "password must be 8 to 32 characters long, containing at least 1 uppercase, 1 lowercase, 1 special character and 1 digit")
  private String password;

  private Boolean isUserLoggedIn;

  public UserDto() {}

  public UserDto(String username, String email, String password, Boolean isUserLoggedIn) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.isUserLoggedIn = isUserLoggedIn;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean getUserLoggedIn() {
    return isUserLoggedIn;
  }

  public void setUserLoggedIn(Boolean userLoggedIn) {
    isUserLoggedIn = userLoggedIn;
  }
}
