package com.sun.service;

import com.sun.pojo.Teacher;
import org.apache.ibatis.annotations.Param;

public interface TeacherService {

    //教师登录校验
    String teacherLoginCheck(String userName);

    //通过ID查找教师
    Teacher queryTeacherById(int id);

    //教师用户注册
    int teacherRegistered(int id, String userName, String psw);

}
