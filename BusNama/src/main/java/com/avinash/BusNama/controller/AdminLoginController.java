package com.avinash.BusNama.controller;

import com.avinash.BusNama.exception.AdminException;
import com.avinash.BusNama.exception.LoginException;
import com.avinash.BusNama.model.CurrentAdminSession;
import com.avinash.BusNama.model.DTO.AdminLoginDTO;
import com.avinash.BusNama.service.AdminLoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/busnama")
public class AdminLoginController {

    @Autowired
    private AdminLoginService loginService;


    @PostMapping("/admin/login")
    public ResponseEntity<CurrentAdminSession> loginAdmin(@Valid @RequestBody AdminLoginDTO loginDTO)throws AdminException, LoginException{
        CurrentAdminSession currentAdminSession = loginService.adminLogin(loginDTO);
        return  new ResponseEntity<>(currentAdminSession, HttpStatus.ACCEPTED);
    }

    @PostMapping("/admin/logout")
    public String logoutAdmin(@RequestParam(required = false)String key)throws LoginException{
        return loginService.adminLogout(key);
    }
}
