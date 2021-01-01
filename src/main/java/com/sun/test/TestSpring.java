package com.sun.test;

import com.sun.pojo.PageBean;
import com.sun.pojo.Stu;
import com.sun.pojo.Teacher;
import com.sun.service.*;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestSpring {


    @Test
    public void a() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        StuService stuServiceImpl = context.getBean("stuServiceImpl", StuServiceImpl.class);
        String a = "a";
        int i = 2;
        try {
            i = Integer.parseInt(a);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        System.out.println(i);
    }

    @Test
    public void score() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");
    TeacherService teacherService = context.getBean("teacherServiceImpl", TeacherServiceImpl.class);
        teacherService.teacherRegistered(1, "zhangsan", "zhangsana");
        System.out.println(teacherService.queryTeacherById(1));
}
    //登录检验
    @Test
    public void loginCheck() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        AdminService adminService = context.getBean("adminServiceImpl", AdminServiceImpl.class);
        String psw = adminService.adminLoginCheck("wangzhuren");
        System.out.println(psw);
    }
}
