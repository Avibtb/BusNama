package com.avinash.BusNama.service;

import com.avinash.BusNama.exception.AdminException;
import com.avinash.BusNama.exception.UserException;
import com.avinash.BusNama.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user) throws UserException;
    User updateUser(User user, String key)throws UserException;
    User deleteUser(Integer userId, String key)throws UserException, AdminException;
    User viewUserById(Integer userId, String key)throws UserException, AdminException;
    List<User> viewAllUser(String key)throws UserException, AdminException;
}
