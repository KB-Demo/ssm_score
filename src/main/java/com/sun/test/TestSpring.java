package com.sun.test;

import com.sun.pojo.*;
import com.sun.pojo.Class;
import com.sun.service.*;
import com.sun.utils.PageBean;
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
        Map map = new HashMap();
        map.put("startIndex", 0);
        map.put("rows", 100);
        map.put("sid", 0);
        map.put("sname", "");
        map.put("class_name", "");
        //PageBean<Stu> stuPageBean = adminService.adminStuPerson("", "", "0", "100", "a");
        //PageBean<Teacher> stuPageBean = adminService.adminTeacherPerson("", "0", "0", "100");
        //PageBean<Score> stuPageBean = adminService.adminStuScore("","","",  "0", "100");
        /*List<Score> list = stuPageBean.getList();
        for (Score stu : list) {
            System.out.println(stu);
        }*/
       /* List<Course> courses = adminService.adminTeacherCourse("", "");
        for (Course cours : courses) {
            System.out.println(cours);
        }
        List<Class> classes = adminService.adminClassScore("", "");
        for (Class aClass : classes) {
            System.out.println(aClass);
        }*/
       //new Stu(null,"张张","1997-5-06","女","156456165","156156@qq.com","123456","123456")
      /* Stu stu = new Stu();
        stu.setS_name("张张");
        stu.setS_birth("1997-5-06");
        stu.setS_sex("女");
        int i = adminService.addStu(stu);
        System.out.println(i);*/
        /*List<Class> classes = adminService.adminTeacherClass("", "");
        for (Class aClass : classes) {
            System.out.println(aClass);
        }*/
        List<Class> classes = adminService.adminTeacherClass("", "张");
        for (Class aClass : classes) {
            System.out.println(aClass);
        }
    }
}
