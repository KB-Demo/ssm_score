package com.sun.controller;

import com.sun.pojo.Stu;
import com.sun.pojo.Teacher;
import com.sun.service.StuService;
import com.sun.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisteredController {

    @Autowired
    StuService stuService;

    @Autowired
    TeacherService teacherService;

    //跳转注册页面
    @RequestMapping("toRegistered")
    public String toRegistered() {
        System.out.println("跳转注册页面");
        return "registered";
    }

    //注册页面
    @RequestMapping("registeredUser")
    public String registered(Model model, @RequestParam("register") String register, @RequestParam("userName") String userName, @RequestParam("psw") String psw, @RequestParam("checkPsw") String checkPsw, @RequestParam("id") String _id) {
        System.out.println("注册页面");
        //前端有判断非空
        //if (userName == null || psw == null || checkPsw == null || id == null || "".equals(userName) || "".equals(psw) || "".equals(checkPsw) || "".equals(id))
        if ("".equals(userName) || "".equals(psw) || "".equals(checkPsw) || "".equals(_id)) {
            model.addAttribute("regist_msg", "不能输入非法符号");
            return "registered";
        }
        //防止用户输入非数字的数据
        int id = 0;
        try {
            id = Integer.parseInt(_id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        userName = userName.trim();
        psw = psw.trim();
        checkPsw = checkPsw.trim();
        //注册用户是学生
        if ("stu".equals(register)) {
            System.out.println("学生用户注册");
            Stu stu = stuService.queryStuById(id);
            if (stu == null) {//查询是否有该学生
                model.addAttribute("regist_msg", "没有该学号的学生！");
                return "registered";
            } else if (stu.getS_userName() != null) {//查询该学生是否注册过
                model.addAttribute("regist_msg", "亲爱的同学，你已经注册过了");
                return "registered";
            } else if (userName.contains(" ") || psw.contains(" ") || checkPsw.contains(" ")) {
                model.addAttribute("regist_msg","账号或密码不能带有特殊字符");
                return "registered";
            } else if (!psw.equals(checkPsw)) {
                model.addAttribute("regist_msg", "两次密码输入不一致");
                return "registered";
            }
            stuService.stuRegistered(id, userName, psw);
            model.addAttribute("login_msg", "注册成功");
            model.addAttribute("logger","stu");
            System.out.println("注册成功");
            return "login";
        } else {
            //注册用户是老师
            System.out.println("学生用户注册");
            Teacher teacher = teacherService.queryTeacherById(id);
            if (teacher == null) {//查询是否有该学生
                model.addAttribute("regist_msg", "没有该教师号的老师！");
                return "registered";
            } else if (teacher.getT_userName() != null) {//查询该学生是否注册过
                model.addAttribute("regist_msg", "亲爱的教师，你已经注册过了");
                return "registered";
            } else if (userName.contains(" ") || psw.contains(" ") || checkPsw.contains(" ")) {
                model.addAttribute("账号或密码不能带有特殊字符");
                return "registered";
            } else if (!psw.equals(checkPsw)) {
                model.addAttribute("regist_msg", "两次密码输入不一致");
                return "registered";
            }
            teacherService.teacherRegistered(id, userName, psw);
            model.addAttribute("login_msg", "注册成功");
            model.addAttribute("logger","teacher");
            return "redirect:/login";
        }

    }

    //返回登录界面
    @RequestMapping("toLogin")
    public String toLogin() {
        return "login";
    }
}
