package com.sun.dao;

import org.apache.ibatis.annotations.Param;

public interface AdminMapper {

    //管理员登录校验
    String adminLoginCheck(@Param("userName") String userName);
}
