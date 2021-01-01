package com.sun.dao;

import com.sun.pojo.Teacher;
import org.apache.ibatis.annotations.Param;

public interface TeacherMapper {

    //教师登录校验
    String teacherLoginCheck(@Param("userName") String userName);

    //通过ID查找教师
    Teacher queryTeacherById(@Param("t_id") int id);

    //教师用户注册
    int teacherRegistered(@Param("t_id") int id, @Param("username") String userName, @Param("psw") String psw);
    }
