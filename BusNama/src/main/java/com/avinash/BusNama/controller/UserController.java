package com.avinash.BusNama.controller;

import com.avinash.BusNama.exception.UserException;
import com.avinash.BusNama.model.User;
import com.avinash.BusNama.service.UserService;
import com.avinash.BusNama.serviceImpl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/busnama")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/user/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) throws UserException{
        User savedUser = userService.createUser(user);
        return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
    }


}
