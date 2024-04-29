package com.blogapp.controllers;

import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.UserDto;
import com.blogapp.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // POST - Create User
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        userDto = userService.createUser(userDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    //  PUT - Update User
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer userId, @RequestBody UserDto userDto) {
        userDto = userService.updateUser(userDto, userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    //  Delete - Delete User
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return new ResponseEntity(new ApiResponse("User Deleted Successfully", "true"), HttpStatus.OK);
    }

    //  Get - Get User
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Integer userId) {
        UserDto userDto = userService.getUserById(userId);
        return new ResponseEntity<>(userDto, HttpStatus.FOUND);
    }

    //  Get - Get All User
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.FOUND);
    }
}
