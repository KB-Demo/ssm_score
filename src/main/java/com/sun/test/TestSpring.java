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
        Stu stu = stuServiceImpl.queryStuByUserName("zhaolei");
        PageBean<Stu> courseRankByPage1 = stuServiceImpl.findCourseRankByPage("1", "25", "", stu);
        List<Stu> list1 = courseRankByPage1.getList();
        for (Stu stu1 : list1) {
            System.out.println(stu1);
        }
        System.out.println("______________________________________________________________________");
        PageBean<Stu> courseRankByPage = stuServiceImpl.findCourseRankByPage("4", "5", "", stu);
        List<Stu> list = courseRankByPage.getList();
        for (Stu stu1 : list) {
            System.out.println(stu1);
        }

    }

    @Test
    public void score() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        TeacherService teacherService = context.getBean("teacherServiceImpl", TeacherServiceImpl.class);
        PageBean<Teacher> zhangsan = teacherService.findClassStuSumRank("1", "10", "0", "zhangsan", "");
        Boolean aBoolean = teacherService.checkAddStu("6", "2", "88");
        System.out.println(aBoolean);
    }
    //登录检验
    @Test
    public void loginCheck() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        AdminService adminService = context.getBean("adminServiceImpl", AdminServiceImpl.class);
        String a = "";
        String error=null;
        int i = 0;
        try {
            i = Integer.parseInt(a);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            error = "为查";
        }
        System.out.println(error);
    }
}
