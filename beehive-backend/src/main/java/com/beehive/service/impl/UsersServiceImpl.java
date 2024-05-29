package com.beehive.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.beehive.dto.UsersDto;
import com.beehive.entity.UsersEntity;
import com.beehive.repository.UsersRepository;
import com.beehive.request.UserRequest;
import com.beehive.response.UserResponse;
import com.beehive.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired UsersRepository usersRepository;

    @Override
    public UserResponse getAllUsers() {
        
        UserResponse response = new UserResponse();
        try {
            Iterable<UsersEntity> users = usersRepository.findAll();
            List<UsersDto> usersDto = new ArrayList<>();
            users.forEach(entity -> {
                UsersDto dto = new UsersDto();
                dto.setContactNumber(entity.getContactNumber());
                dto.setEmailId(entity.getEmailId());
                dto.setFirstName(entity.getFirstName());
                dto.setLastName(entity.getLastName());
                dto.setLoginId(entity.getLoginId());
                dto.setPassword(entity.getPassword());
                usersDto.add(dto);
            });
            response.setUsersDto(usersDto);
            response.setStatusMessage("SUCCESS");
        } catch (
                Exception e) {
            response.setStatusMessage("FAILURE");
        }

        return response;
    }

    @Override
    public UserResponse register(UserRequest request) {
        
        UserResponse response = new UserResponse();
        UsersEntity entity = new UsersEntity();
        UsersDto userDto = request.getUsersDTO();
        try {
            Optional<UsersEntity> user = usersRepository.findById(userDto.getLoginId());
            if (user.isEmpty()) {
                entity.setContactNumber(userDto.getContactNumber());
                entity.setEmailId(userDto.getEmailId());
                entity.setFirstName(userDto.getFirstName());
                entity.setLastName(userDto.getLastName());
                entity.setLoginId(userDto.getLoginId());
                entity.setPassword(passwordEncoder().encode(userDto.getPassword()));
                usersRepository.save(entity);
                response.setStatusMessage("SUCCESS");
            } else {
                response.setStatusMessage("User Already Exists");
            }
        } catch (
                Exception e) {
            e.printStackTrace();
            response.setStatusMessage("FAILURE");
        }

        return response;
    }

    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public UserResponse forgetPassword(UserRequest request) {
        
        UserResponse response = new UserResponse();
        UsersDto userDto = request.getUsersDTO();
        try {
            Optional<UsersEntity> optionalUser = usersRepository.findByLoginId(userDto.getLoginId());
            if (optionalUser.isPresent()) {
                UsersEntity user = optionalUser.get();
                user.setPassword(passwordEncoder().encode(userDto.getPassword()));
                usersRepository.save(user);
                response.setStatusMessage("SUCCESS");
            } else {
                response.setStatusMessage("USER NOT FOUND");
            }
        } catch (
                Exception e) {
            response.setStatusMessage("FAILURE");
        }
        return response;
    }

    @Override
    public UserResponse searchUsers(String username) {
        
        UserResponse response = new UserResponse();
        List<UsersDto> users = new ArrayList<UsersDto>();
        Optional<UsersEntity> optionalEntities = usersRepository.findByLoginId(username);
        try {
            UsersEntity entities = optionalEntities.get();
            List<UsersEntity> entitiesList = new ArrayList<>();
            entitiesList.add(entities);
            entitiesList.forEach(e -> {
                UsersDto dto = new UsersDto();
                dto.setLoginId(e.getLoginId());
                dto.setFirstName(e.getFirstName());
                dto.setLastName(e.getLastName());
                users.add(dto);
            });
            response.setStatusMessage("SUCCESS");
            response.setUsersDto(users);
        } catch (
                Exception e) {
            e.printStackTrace();
            response.setStatusMessage("FAILURE");
        }
        return response;
    }
}
