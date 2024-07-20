package com.beehive.service.impl;

import com.beehive.dto.UserDto;
import com.beehive.entity.UserEntity;
import com.beehive.exception.UserNotFoundException;
import com.beehive.repository.UserRepository;
import com.beehive.request.UserRequest;
import com.beehive.response.UserResponse;
import com.beehive.service.UserService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Boolean serviceUserCheckIfExists(String username) {
    return userRepository.existsById(username);
  }

  @Override
  public UserResponse serviceUserGetAll() {

    UserResponse response = new UserResponse();
    try {
      Iterable<UserEntity> users = userRepository.findAll();
      List<UserDto> userDtoList = new ArrayList<>();
      users.forEach(
          entity -> {
            UserDto userDto = new UserDto();
            userDto.setEmail(entity.getEmail());
            userDto.setUsername(entity.getUsername());
            userDto.setPassword(entity.getPassword());
            userDtoList.add(userDto);
          });
      response.setUsers(userDtoList);
      response.setStatusMessage("SUCCESS");
    } catch (Exception e) {
      logger.error("in serviceUserGetAll: ", e);
      response.setStatusMessage("FAILURE");
    }

    return response;
  }

  @Override
  @Transactional
  public UserResponse serviceUserRegister(UserRequest userRequest) {

    UserResponse userResponse = new UserResponse(new ArrayList<>(), "");
    UserEntity userEntity = new UserEntity();
    UserDto userDto = userRequest.getUser();
    try {
      if (!userRepository.existsById(userDto.getUsername())) {
        userEntity.setEmail(userDto.getEmail());
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(passwordEncoder().encode(userDto.getPassword()));
        userRepository.save(userEntity);
        userResponse.setStatusMessage("SUCCESS");
      } else {
        userResponse.setStatusMessage("User Already Exists");
      }
    } catch (Exception e) {
      logger.error("in serviceUserRegister: ", e);
      userResponse.setStatusMessage("FAILURE");
    }

    return userResponse;
  }

  @Override
  public UserResponse serviceUserForgotPassword(UserRequest request) {

    UserResponse response = new UserResponse();
    UserDto userDto = request.getUser();
    try {
      Optional<UserEntity> optionalUser = userRepository.findByUsername(userDto.getUsername());
      if (optionalUser.isPresent()) {
        UserEntity user = optionalUser.get();
        user.setPassword(passwordEncoder().encode(userDto.getPassword()));
        userRepository.save(user);
        response.setStatusMessage("SUCCESS");
      } else {
        response.setStatusMessage("USER NOT FOUND");
      }
    } catch (Exception e) {
      logger.error("in serviceUserForgotPassword: ", e);
      response.setStatusMessage("FAILURE");
    }
    return response;
  }

  @Override
  public UserResponse serviceUserSearch(String username) throws UserNotFoundException {

    UserResponse response = new UserResponse();
    List<UserDto> userDtoList = new ArrayList<>();
    Optional<UserEntity> optionalEntities = userRepository.findByUsername(username);
    try {
      if (optionalEntities.isPresent()) {
        UserEntity userEntity = optionalEntities.get();
        List<UserEntity> userEntityList = new ArrayList<>();
        userEntityList.add(userEntity);
        userEntityList.forEach(
            entity -> {
              UserDto userDto = new UserDto();
              userDto.setUsername(entity.getUsername());
              userDto.setEmail(entity.getEmail());
              userDtoList.add(userDto);
            });
        response.setStatusMessage("SUCCESS");
        response.setUsers(userDtoList);
      }
    } catch (Exception e) {
      logger.error("in serviceUserSearch: ", e);
      response.setStatusMessage("FAILURE");
    }
    return response;
  }

  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}
