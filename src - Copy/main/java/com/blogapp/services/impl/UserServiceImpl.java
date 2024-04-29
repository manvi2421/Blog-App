package com.blogapp.services.impl;

import com.blogapp.entities.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.UserDto;
import com.blogapp.repositories.UserRepository;
import com.blogapp.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;



    private User dtoToEntity(UserDto userDto) {
        User user = modelMapper.map(userDto , User.class);
//        User user = new User();
//        user.setId(userDto.getId());
//        user.setAbout(userDto.getAbout());
//        user.setEmail(userDto.getEmail());
//        user.setName(userDto.getName());
//        user.setPassword(userDto.getPassword());

        return user;
    }

    private UserDto entityToDto(User user) {
        UserDto userDto = modelMapper.map(user , UserDto.class);
//        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setAbout(user.getAbout());
//        userDto.setEmail(user.getEmail());
//        userDto.setName(user.getName());
//        userDto.setPassword(user.getPassword());

        return userDto;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = dtoToEntity(userDto);
        user = userRepository.save(user);
        return entityToDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User"," id " ,userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());

        user = userRepository.save(user);
        return entityToDto(user);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User" , " id " , userId));
        return entityToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return userDtoList;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " id ", userId));
        userRepository.delete(user);
    }
}
