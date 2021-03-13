package com.sun.controller;

import com.sun.utils.PageBean;
import com.sun.pojo.Stu;
import com.sun.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/stu")
public class StuController {

    @Autowired
    StuService stuService;


    //跳转个人成绩页面
    @RequestMapping("/stuScore")
    public String allStu(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        List<Stu> stus = stuService.queryStuScoreCourse(username, "");
        model.addAttribute("stus", stus);
        return "student/stuIndex";
    }

    //跳转个人信息页面,session中存有stu信息
    @RequestMapping("/stuPersonal")
    public String stuPerson(HttpSession session) {
        Stu stu = stuService.queryStuByUserName((String) session.getAttribute("username"));
        session.setAttribute("stu", stu);
        return "student/stuPersonal";
    }

    //跳转修改学生信息页面,session中存有stu信息
    @RequestMapping("/toUpdateStu")
    public String toUpdateStu() {
        return "student/stuUpdate";
    }

    //修改学生信息，并跳转个人信息页面
    @RequestMapping("/updateStu")
    public String updateStu(HttpSession session, Stu stu) {
        Stu stu1 = (Stu) session.getAttribute("stu");
        System.out.println(stu1 + "修改前");
        int i = stuService.updateStu(stu);
        stu.setS_class(stu1.getS_class());
        System.out.println(stu + "修改后");
        if (i == 1) {
            System.out.println("修改成功");
            session.setAttribute("stu", stu);
        }
        return "redirect:/stu/stuPersonal";
    }

    //通过课程名称查询
    @RequestMapping("/queryByCname")
    public String queryByCname(HttpSession session, Model model, String cname) {
        String username = (String) session.getAttribute("username");
        List<Stu> stus = stuService.queryStuScoreCourse(username, cname);
        model.addAttribute("stus", stus);
        if (stus.size() == 0) model.addAttribute("error", "未查到该课程");
        return "student/stuIndex";
    }

    //跳转平均成绩查询页面
    @RequestMapping("stuSumRank")
    public String stuSumRank(HttpSession session, Model model, String currentPage, String rows) {
        if (currentPage == null || "".equals(currentPage)) {
            currentPage = "1";
        }
        if (rows == null || "".equals(rows)) {
            rows = "10";
        }
        //获取学生对象
        Stu stu = (Stu)session.getAttribute("stu");
        PageBean<Stu> pb = stuService.findRankByPage(currentPage, rows, stu);
        List<Stu> stus = pb.getList();
        model.addAttribute("stus", stus);
        model.addAttribute("pb", pb);
        return "student/stuRank";
    }

    //跳转每科成绩排名页面
    @RequestMapping("queryRankByCname")
    public String queryRankByCname(HttpSession session, Model model, String currentPage, String rows, String cname) {
        if (currentPage == null || "".equals(currentPage)) currentPage = "1";
        if (rows == null || "".equals(rows)) rows = "10";
        if (cname == null || "".equals(cname)) cname = "";
        System.out.println(currentPage);
        //获取学生对象
        Stu stu = (Stu) session.getAttribute("stu");
        PageBean<Stu> pb = stuService.findCourseRankByPage(currentPage, rows, cname, stu);
        List<Stu> stus = pb.getList();
        model.addAttribute("stus", stus);
        model.addAttribute("pb", pb);
        model.addAttribute("cname", cname);
        model.addAttribute("error", pb.getError());//查询与否消息
        return "student/stuRankByCourse";
    }

    //跳转修改密码页面
    @RequestMapping("toChangePsw")
    public String toChangePsw() {
        return "student/stuChangePsw";
    }

    //修改密码，成功跳转登录页面，失败跳转修改页面
    @RequestMapping("changePsw")
    public String changePsw(HttpSession session, Model model,String oldPsw,String newPsw,String confirmPsw) {
        String username = (String) session.getAttribute("username");
        String psw = stuService.stuLoginCheck(username);
        if ("".equals(oldPsw) || oldPsw == null) {
            model.addAttribute("error_OldPsw", "   请输入正确格式！");
            return "student/stuChangePsw";
        }
        if (newPsw == null || "".equals(newPsw) || confirmPsw == null || "".equals(confirmPsw)) {
            model.addAttribute("error_NewPsw", "   请输入正确格式！");
            return "student/stuChangePsw";
        }
        //去除首位字符串
        System.out.println("newPsw:"+newPsw+" confirmPsw:"+confirmPsw);
        oldPsw = oldPsw.trim();
        newPsw = newPsw.trim();
        confirmPsw = confirmPsw.trim();
        System.out.println("newPsw:"+newPsw+" confirmPsw:"+confirmPsw);
        if (!psw.equals(oldPsw)) {
            model.addAttribute("error_OldPsw", "   旧密码错误！");
            return "student/stuChangePsw";
        } else if (!newPsw.equals(confirmPsw)) {
            model.addAttribute("error_NewPsw", "   请输入相同的新密码！");
            return "student/stuChangePsw";
        } else if (psw.equals(newPsw)) {
            model.addAttribute("error_NewPsw", "   新旧密码相同,请重新输入！");
            return "student/stuChangePsw";
        } else if (newPsw.contains(" ")) {
            model.addAttribute("error_NewPsw", "   密码不能包含特殊字符！");
            return "student/stuChangePsw";
        } else {
            stuService.updateStuPsw(username, newPsw);
            model.addAttribute("login_msg", "密码修改请重新登录");
            session.removeAttribute("username");
            return "login";
        }
    }

    //退出登录
    @RequestMapping("exit")
    public String exit(HttpSession session,Model model) {
        session.removeAttribute("username");
        model.addAttribute("login_msg", "退出成功");
        return "login";
    }
}
