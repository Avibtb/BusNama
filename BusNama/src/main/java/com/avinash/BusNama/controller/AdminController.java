package com.avinash.BusNama.controller;

import com.avinash.BusNama.exception.AdminException;
import com.avinash.BusNama.model.Admin;
import com.avinash.BusNama.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/busnama")
public class AdminController {

    @Autowired private AdminService adminService;

    @PostMapping("/admin/register")
    public ResponseEntity<Admin> registerAdmin(@Valid @RequestBody Admin admin) throws AdminException {
        Admin savedAdmin = adminService.createAdmin(admin);
        return new ResponseEntity<>(savedAdmin, HttpStatus.CREATED);
    }

    @PutMapping("/admin/update")
    public ResponseEntity<Admin> updateAdmin(@Valid @RequestBody Admin admin,@RequestBody(required = false)String key)throws AdminException{
        Admin updateAdmin = adminService.updateAdmin(admin,key);
        return new ResponseEntity<>(updateAdmin,HttpStatus.OK);

    }

}
