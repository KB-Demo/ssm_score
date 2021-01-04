package com.sun.controller;

import com.sun.pojo.PageBean;
import com.sun.pojo.Teacher;
import com.sun.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("teacher")
@Controller
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    //教师首页，个人课程
    @RequestMapping("index")
    public String teacherIndex(HttpSession session, Model model) {
        System.out.println("来到teacherIndex");
        String username = (String) session.getAttribute("username");
        List<Teacher> teachers = teacherService.queryCourse(username);
        model.addAttribute("teachers", teachers);
        return "teacher/teacherIndex";
    }

    //跳转个人信息
    @RequestMapping("personal")
    public String teacherPersonal() {
        return "teacher/teacherPersonal";
    }

    //跳转个人信息修改
    @RequestMapping("toUpdate")
    public String toUpdate() {
        return "teacher/teacherUpdate";
    }

    //个人信息修改
    @RequestMapping("updateTeacher")
    public String updateTeacher(Teacher teacher,HttpSession session) {
        Teacher teacher1 = (Teacher) session.getAttribute("teacher");
        teacherService.updateTeacher(teacher);
        teacher.setT_class(teacher1.getT_class());
        session.setAttribute("teacher",teacher);
        return "redirect:/teacher/personal";
    }

    //跳转课程学生页面
    @RequestMapping("myCourseStu")
    public String myCourseStu(HttpSession session, Model model, String currentPage, String rows, String sname,String sid) {
        if (sname == null || "".equals(sname)) sname = "";//防止cname为null时查不出数据
        if (currentPage == null||"".equals(currentPage)) currentPage = "1";
        if (rows == null || "".equals(rows)) rows = "10";
        if (sid == null || "".equals(sid)) sid = "0";
        String username = (String) session.getAttribute("username");
        PageBean<Teacher> pb = teacherService.findCourseStu(currentPage, rows, sid, username, sname);
        List<Teacher> teachers = pb.getList();
        model.addAttribute("teachers", teachers);
        model.addAttribute("sname", sname);
        model.addAttribute("sid", sid);
        model.addAttribute("pb", pb);
        return "teacher/teacherCourseStu";
    }

    //跳转班级学生页面
    @RequestMapping("myClassStu")
    public String myClassStu(HttpSession session, Model model, String currentPage, String rows, String sname,String sid) {
        if (sname == null || "".equals(sname)) sname = "";//防止cname为null时查不出数据
        if (currentPage == null||"".equals(currentPage)) currentPage = "1";
        if (rows == null || "".equals(rows)) rows = "10";
        if (sid == null || "".equals(sid)) sid = "0";
        String username = (String) session.getAttribute("username");
        PageBean<Teacher> pb = teacherService.findClassStu(currentPage, rows, sid, username, sname);
        List<Teacher> teachers = pb.getList();
        if (teachers.size()==0) pb.setError("未查到该学生！");
        model.addAttribute("teachers", teachers);
        model.addAttribute("sname", sname);
        model.addAttribute("sid", sid);
        model.addAttribute("pb", pb);
        return "teacher/teacherClassStu";
    }

    //跳转课程学生成绩
    @RequestMapping("courseStuScore")
    public String courseStuScore(HttpSession session, Model model, String currentPage, String rows, String sname,String sid,String cname) {
        if (sname == null || "".equals(sname)) sname = "";//防止cname为null时查不出数据
        if (cname == null || "".equals(cname)) cname = "";
        if (currentPage == null||"".equals(currentPage)) currentPage = "1";
        if (rows == null || "".equals(rows)) rows = "10";
        if (sid == null || "".equals(sid)) sid = "0";
        String username = (String) session.getAttribute("username");
        PageBean<Teacher> pb = teacherService.findCourseStuRank(currentPage, rows, sid, username, sname,cname);
        List<Teacher> teachers = pb.getList();
        if (teachers.size()==0) pb.setError("未查到该内容!");
        model.addAttribute("teachers", teachers);
        model.addAttribute("sname", sname);
        model.addAttribute("sid", sid);
        model.addAttribute("pb", pb);
        model.addAttribute("cname", cname);
        return "teacher/teacherCourseStuRank";
    }

    //跳转班级学生成绩
    @RequestMapping("classStuScore")
    public String classStuScore() {
        return "";
    }

    //跳转修改密码页面
    @RequestMapping("toChangePsw")
    public String toChangePsw() {
        return "";
    }

    //退出登录
    @RequestMapping("exit")
    public String exit(HttpSession session) {
        session.removeAttribute("username");
        session.setAttribute("login_msg","退出成功");
        return "login";
    }

}
