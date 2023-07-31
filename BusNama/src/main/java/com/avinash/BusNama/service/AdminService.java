package com.avinash.BusNama.service;

import com.avinash.BusNama.exception.AdminException;
import com.avinash.BusNama.model.Admin;

public interface AdminService {
    public Admin createAdmin(Admin admin)throws AdminException;
    public Admin updateAdmin(Admin admin,String key)throws AdminException;
}
