package com.avinash.BusNama.serviceImpl;

import com.avinash.BusNama.exception.AdminException;
import com.avinash.BusNama.model.Admin;
import com.avinash.BusNama.model.CurrentAdminSession;
import com.avinash.BusNama.repository.AdminRepository;
import com.avinash.BusNama.repository.CurrentAdminSessionRepository;
import com.avinash.BusNama.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CurrentAdminSessionRepository currentAdminSessionRepository;

    @Override
    public Admin createAdmin(Admin admin) throws AdminException {
        Admin a = adminRepository.findByEmail(admin.getEmail());
        if(a != null)throw new AdminException("Admin is already register with email (" +a.getEmail() + ")");
        return adminRepository.save(admin);
    }

    @Override
    public Admin updateAdmin(Admin admin, String key) throws AdminException {
        CurrentAdminSession adminSession = currentAdminSessionRepository.findByaid(key);
        if(adminSession == null) throw new AdminException("Please enter valid key or login first!");
        if(admin.getAdminID() != adminSession.getAdminID()) throw new AdminException("Invalid admin details,please login for updating admin!");
        return adminRepository.save(admin);
    }
}
