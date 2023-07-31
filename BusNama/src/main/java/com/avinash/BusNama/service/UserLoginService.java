package com.avinash.BusNama.service;

import com.avinash.BusNama.exception.LoginException;
import com.avinash.BusNama.model.CurrentUserSession;
import com.avinash.BusNama.model.DTO.UserLoginDTO;

public interface UserLoginService {
    public CurrentUserSession userLogin(UserLoginDTO userLoginDTO)throws LoginException;
    public String userLogOut(String key)throws LoginException;
}
