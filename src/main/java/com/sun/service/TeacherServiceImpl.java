package com.sun.service;

import com.sun.dao.TeacherMapper;
import com.sun.pojo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherMapper teacherMapper;

    @Override
    public String teacherLoginCheck(String userName) {
        return teacherMapper.teacherLoginCheck(userName);
    }

    @Override
    public Teacher queryTeacherById(int id) {
        return teacherMapper.queryTeacherById(id);
    }

    @Override
    public int teacherRegistered(int id, String userName, String psw) {
        return teacherMapper.teacherRegistered(id, userName, psw);
    }

}
