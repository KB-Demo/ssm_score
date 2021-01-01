package com.sun.service;

import com.sun.dao.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;


    @Override
    public String adminLoginCheck(String userName) {
        return adminMapper.adminLoginCheck(userName);
    }

}
