package com.avinash.BusNama.controller;

import com.avinash.BusNama.exception.LoginException;
import com.avinash.BusNama.model.CurrentUserSession;
import com.avinash.BusNama.model.DTO.UserLoginDTO;
import com.avinash.BusNama.service.UserLoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/busnama")
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;

    @PostMapping("/user/login")
    public ResponseEntity<CurrentUserSession> logInUser(@Valid @RequestBody UserLoginDTO userLoginDTO)throws LoginException{
        CurrentUserSession currentUserSession= userLoginService.userLogin(userLoginDTO);
        return new ResponseEntity<CurrentUserSession>(currentUserSession, HttpStatus.ACCEPTED);
    }

    @PostMapping("/user/logout")
    public String logoutUser(@RequestParam(required = false) String key)throws LoginException{
        return userLoginService.userLogOut(key);
    }
}
