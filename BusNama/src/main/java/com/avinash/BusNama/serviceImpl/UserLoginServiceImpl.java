package com.avinash.BusNama.serviceImpl;

import com.avinash.BusNama.exception.LoginException;
import com.avinash.BusNama.model.CurrentUserSession;
import com.avinash.BusNama.model.DTO.UserLoginDTO;
import com.avinash.BusNama.model.User;
import com.avinash.BusNama.repository.CurrentUserSessionRepository;
import com.avinash.BusNama.repository.UserRepository;
import com.avinash.BusNama.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurrentUserSessionRepository userSessionRepository;

    @Override
    public CurrentUserSession userLogin(UserLoginDTO userLoginDTO) throws LoginException {
        User registeredUser = userRepository.findByEmail(userLoginDTO.getEmail());
        if(registeredUser == null)throw new LoginException("Please enter valid email!");

        Optional<CurrentUserSession> loggedInUser = userSessionRepository.findById(registeredUser.getUserID());
        if (loggedInUser.isPresent()) throw new LoginException("User already Logged!");

        if(registeredUser.getPassword().equals(userLoginDTO.getPassword())){
            String key = "Busnama123";
            CurrentUserSession currentUserSession = new CurrentUserSession();
            currentUserSession.setUserID(registeredUser.getUserID());
            currentUserSession.setUuid(key);
            currentUserSession.setTime(LocalDateTime.now());
            return userSessionRepository.save(currentUserSession);
        }else
            throw new LoginException("Please enter a valid password!");

    }

    @Override
    public String userLogOut(String key) throws LoginException {
        CurrentUserSession loggedInUser = userSessionRepository.findByUuid(key);
        if(loggedInUser == null) throw new LoginException("Please enter a valid key or login first");
        userSessionRepository.delete(loggedInUser);
        return "User logged out!";
    }
}
