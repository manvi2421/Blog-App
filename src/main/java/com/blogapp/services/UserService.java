package com.blogapp.services;

import com.blogapp.payloads.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto,Integer userId);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();
    void deleteUser(Integer userId);

}
