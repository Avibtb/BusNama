package com.avinash.BusNama.service;

import com.avinash.BusNama.exception.AdminException;
import com.avinash.BusNama.exception.LoginException;
import com.avinash.BusNama.model.CurrentAdminSession;
import com.avinash.BusNama.model.DTO.AdminLoginDTO;

public interface AdminLoginService {
    CurrentAdminSession adminLogin(AdminLoginDTO loginDTO)throws LoginException, AdminException;
    String adminLogout(String key)throws LoginException;
}
