package com.sun.service;

import org.apache.ibatis.annotations.Param;

public interface AdminService {

    //管理员登录校验
    String adminLoginCheck(String userName);
}
