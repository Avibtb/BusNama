package com.avinash.BusNama.serviceImpl;

import com.avinash.BusNama.exception.AdminException;
import com.avinash.BusNama.exception.UserException;
import com.avinash.BusNama.model.CurrentAdminSession;
import com.avinash.BusNama.model.CurrentUserSession;
import com.avinash.BusNama.model.User;
import com.avinash.BusNama.repository.CurrentAdminSessionRepository;
import com.avinash.BusNama.repository.CurrentUserSessionRepository;
import com.avinash.BusNama.repository.UserRepository;
import com.avinash.BusNama.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurrentUserSessionRepository userSessionRepository;

    @Autowired
    private CurrentAdminSessionRepository adminSessionRepository;

    @Override
    public User createUser(User user) throws UserException {
        User registeredUser = userRepository.findByEmail(user.getMobile());
        if(registeredUser != null) throw new UserException("User is already registered!");
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user, String key) throws UserException {
        CurrentUserSession loggedInUser = userSessionRepository.findByUuid(key);
        if(loggedInUser == null) throw new UserException("Please enter a valid key or login first!");
        if(user.getUserID() != loggedInUser.getUserID()) throw new UserException("Invalid user details, please login for updating user!");
        return userRepository.save(user);
    }

    @Override
    public User deleteUser(Integer userId, String key) throws UserException ,AdminException{
        CurrentAdminSession loggedInAdmin = adminSessionRepository.findByaid(key);
        if(loggedInAdmin == null) throw new AdminException("Please enter a valid key or login first!");
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException("Invalid user Id!"));
        userRepository.delete(user);
        return user;
    }

    @Override
    public User viewUserById(Integer userId, String key) throws UserException,AdminException {
        CurrentAdminSession loggedInAdmin = adminSessionRepository.findByaid(key);
        if(loggedInAdmin == null)throw new AdminException("Please enter a valid key or login first!");
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException("Invalid user id"));
        return user;
    }

    @Override
    public List<User> viewAllUser(String key) throws UserException ,AdminException{
        CurrentAdminSession loggedInAdmin = adminSessionRepository.findByaid(key);
        if(loggedInAdmin == null)  throw new AdminException("Please enter a valid key or login first!");
        List<User> list = userRepository.findAll();
        if(list.isEmpty()) throw  new UserException("No user found!");
        return list;
    }
}
