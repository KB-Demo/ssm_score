package com.sun.controller;

import com.sun.pojo.Stu;
import com.sun.service.AdminService;
import com.sun.service.StuService;
import com.sun.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    StuService stuService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    AdminService adminService;

    @RequestMapping("login")
    public String loginCheck(@RequestParam("logger") String logger,@RequestParam("userName") String userName,@RequestParam("psw")String psw, HttpSession session, Model model) {
        System.out.println(logger);
        if ("stu".equals(logger)){
            session.setAttribute("logger","stu");
            String check_psw = stuService.stuLoginCheck(userName);
            System.out.println("学生登录"+userName+"-"+psw);
            if (psw != null && psw.equals(check_psw)) {
                System.out.println("学生账号密码正确");
                Stu stu = stuService.queryStuByUserName(userName);
                session.setAttribute("login_msg", stu.getS_name());
                session.setAttribute("stu",stu);
                session.setAttribute("username",stu.getS_userName());
                System.out.println(stu+"logincontrollerGet");
            /*List<Stu> stus = stuService.queryAllStu();
            model.addAttribute("stus", stus);*/
            return "redirect:/stu/stuScore";
        } else {
            session.setAttribute("login_msg","登录失败,账号或密码错误!");
            return "login";
        }
        }
        //教师登录校验
        else if ("teacher".equals(logger)) {
            //老师登录校验
            session.setAttribute("logger","teacher");
            System.out.println("教师登录"+userName+"-"+psw);
            String check_psw = teacherService.teacherLoginCheck(userName);
            if (psw != null && psw.equals(check_psw)) {
                System.out.println("教师账号密码正确");
                session.setAttribute("login_msg", userName);
                //老师首页
                return "";
            } else {
                session.setAttribute("login_msg","登录失败,账号或密码错误！");
                return "login";
            }
        }
        //管理员登录校验
        else{
            //管理员登录校验
            session.setAttribute("logger","admin");
            System.out.println("管理员登录"+userName+"-"+psw);
            String check_psw = adminService.adminLoginCheck(userName);
            if (psw != null && psw.equals(check_psw)) {
                System.out.println("管理员账号密码正确");
                session.setAttribute("login_msg", userName);
                //管理员首页
                return "";
            } else {
                session.setAttribute("login_msg","登录失败,账号或密码错误！");

                return "login";
            }
        }

    }
}