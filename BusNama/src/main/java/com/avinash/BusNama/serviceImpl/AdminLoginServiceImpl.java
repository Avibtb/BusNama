package com.avinash.BusNama.serviceImpl;

import com.avinash.BusNama.exception.AdminException;
import com.avinash.BusNama.exception.LoginException;
import com.avinash.BusNama.model.Admin;
import com.avinash.BusNama.model.CurrentAdminSession;
import com.avinash.BusNama.model.DTO.AdminLoginDTO;
import com.avinash.BusNama.repository.AdminRepository;
import com.avinash.BusNama.repository.CurrentAdminSessionRepository;
import com.avinash.BusNama.service.AdminLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Autowired
    private CurrentAdminSessionRepository adminSessionRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public CurrentAdminSession adminLogin(AdminLoginDTO loginDTO) throws LoginException, AdminException {
        Admin registeredAdmin = adminRepository.findByEmail(loginDTO.getEmail());
        if(registeredAdmin == null)throw new AdminException("Please enter a valid email");

        Optional<CurrentAdminSession> loggedInAdmin = adminSessionRepository.findById(registeredAdmin.getAdminID());
        if(loggedInAdmin.isPresent())throw new LoginException("Admin is already loggedIn!");

        if(registeredAdmin.getPassword().equals(loginDTO.getPassword())){
            String key ="Busnama123";
            CurrentAdminSession adminSession =new CurrentAdminSession();
            adminSession.setAdminID(registeredAdmin.getAdminID());
            adminSession.setAid(key);
            adminSession.setTime(LocalDateTime.now());
            return adminSessionRepository.save(adminSession);
        }else
            throw new LoginException("Please enter valid password!");
    }

    @Override
    public String adminLogout(String key) throws LoginException {
        CurrentAdminSession currentAdminSession = adminSessionRepository.findByaid(key);
        if (currentAdminSession == null)throw new LoginException("Admin not logged in!");
        adminSessionRepository.delete(currentAdminSession);
        return "Admin logged out";

    }
}
