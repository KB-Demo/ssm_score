package com.sun.controller;

import com.sun.utils.PageBean;
import com.sun.pojo.Teacher;
import com.sun.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
    public String teacherPersonal(HttpSession session) {
        Teacher teacher = teacherService.queryTeacherPersonal((String) session.getAttribute("username"));
        session.setAttribute("teacher",teacher);
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
    public String myClassStu(HttpSession session, Model model, String currentPage, String rows, String sname, String sid) {
        if (sname == null || "".equals(sname)) sname = "";//防止cname为null时查不出数据
        if (currentPage == null || "".equals(currentPage)) currentPage = "1";
        if (rows == null || "".equals(rows)) rows = "10";
        if (sid == null || "".equals(sid)) sid = "0";
        String username = (String) session.getAttribute("username");
        PageBean<Teacher> pb = teacherService.findClassStu(currentPage, rows, sid, username, sname);
        List<Teacher> teachers = pb.getList();
        if (teachers.size() == 0) pb.setError("未查到该学生！");
        model.addAttribute("teachers", teachers);
        model.addAttribute("sname", sname);
        model.addAttribute("sid", sid);
        model.addAttribute("pb", pb);
        return "teacher/teacherClassStu";
    }

    //跳转课程学生成绩
    @RequestMapping("courseStuScore")
    public String courseStuScore(HttpSession session, Model model, String currentPage, String rows, String sname, String sid, String cname) {
        if (sname == null || "".equals(sname)) sname = "";//防止cname为null时查不出数据
        if (cname == null || "".equals(cname)) cname = "";
        if (currentPage == null || "".equals(currentPage)) currentPage = "1";
        if (rows == null || "".equals(rows)) rows = "10";
        if (sid == null || "".equals(sid)) sid = "0";
        String username = (String) session.getAttribute("username");
        PageBean<Teacher> pb = teacherService.findCourseStuRank(currentPage, rows, sid, username, sname, cname);
        List<Teacher> teachers = pb.getList();
        if (teachers.size() == 0) pb.setError("未查到该内容!");
        model.addAttribute("teachers", teachers);
        model.addAttribute("sname", sname);
        model.addAttribute("sid", sid);
        model.addAttribute("pb", pb);
        model.addAttribute("cname", cname);
        return "teacher/teacherCourseStuRank";
    }

    //跳转班级学生成绩
    @RequestMapping("classStuScore")
    public String classStuScore(HttpSession session, Model model, String currentPage, String rows, String sname,String sid,String cname) {
        if (sname == null || "".equals(sname)) sname = "";//防止cname为null时查不出数据
        if (cname == null || "".equals(cname)) cname = "";
        if (currentPage == null||"".equals(currentPage)) currentPage = "1";
        if (rows == null || "".equals(rows)) rows = "10";
        if (sid == null || "".equals(sid)) sid = "0";
        String username = (String) session.getAttribute("username");
        PageBean<Teacher> pb = teacherService.findClassStuRank(currentPage, rows, sid, username, sname, cname);
        List<Teacher> teachers = pb.getList();
        if (teachers.size()==0) pb.setError("未查到该内容!");
        model.addAttribute("teachers", teachers);
        model.addAttribute("sname", sname);
        model.addAttribute("sid", sid);
        model.addAttribute("pb", pb);
        model.addAttribute("cname", cname);
        return "teacher/teacherClassStuRank";
    }

    //跳转班级学生平均成绩
    @RequestMapping("classStuAvgScore")
    public String classStuAvgScore(HttpSession session, Model model, String currentPage, String rows, String sname,String sid) {
        if (sname == null || "".equals(sname)) sname = "";//防止cname为null时查不出数据
        if (currentPage == null||"".equals(currentPage)) currentPage = "1";
        if (rows == null || "".equals(rows)) rows = "10";
        if (sid == null || "".equals(sid)) sid = "0";
        String username = (String) session.getAttribute("username");
        PageBean<Teacher> pb = teacherService.findClassStuAvgRank(currentPage, rows, sid, username, sname);
        List<Teacher> teachers = pb.getList();
        if (teachers.size()==0) pb.setError("未查到该内容!");
        model.addAttribute("pb", pb);
        model.addAttribute("teachers", teachers);
        model.addAttribute("sid", sid);
        model.addAttribute("sname", sname);
        return "teacher/teacherClassStuAvgRank";
    }

    //跳转班级学生总成绩
    @RequestMapping("classStuSumScore")
    public String classStuSumScore(HttpSession session, Model model, String currentPage, String rows, String sname,String sid) {
        if (sname == null || "".equals(sname)) sname = "";//防止cname为null时查不出数据
        if (currentPage == null||"".equals(currentPage)) currentPage = "1";
        if (rows == null || "".equals(rows)) rows = "10";
        if (sid == null || "".equals(sid)) sid = "0";
        String username = (String) session.getAttribute("username");
        PageBean<Teacher> pb = teacherService.findClassStuSumRank(currentPage, rows, sid, username, sname);
        List<Teacher> teachers = pb.getList();
        if (teachers.size()==0) pb.setError("未查到该内容!");
        model.addAttribute("pb", pb);
        model.addAttribute("teachers", teachers);
        model.addAttribute("sid", sid);
        model.addAttribute("sname", sname);
        return "teacher/teacherClassStuSumRank";
    }

    //导出课程学生信息的Excel
    @RequestMapping("courseStuInfo")
    public void courseStuInfo(HttpServletResponse response,HttpSession session,String totalCount,String sid,String sname) throws IOException {
        String username = (String)session.getAttribute("username");
        PageBean<Teacher> pb = teacherService.findCourseStu("0", totalCount, sid, username, sname);
        pb.setExcel("courseStu");
        teacherService.infoExcel(pb, response);
    }

    //导出班级学生信息的Excel
    @RequestMapping("classStuInfo")
    public void classStuInfo(HttpServletResponse response, HttpSession session, String totalCount, String sname, String sid) {
        String username = (String) session.getAttribute("username");
        PageBean<Teacher> pb = teacherService.findClassStu("0", totalCount, sid, username, sname);
        pb.setExcel("classStu");
        teacherService.infoExcel(pb, response);
    }

    //导出课程学生成绩Excel
    @RequestMapping("courseStuRankInfo")
    public void courseStuRankInfo(HttpServletResponse response, HttpSession session, String totalCount, String sname, String sid,String cname) {
        String username = (String) session.getAttribute("username");
        PageBean<Teacher> pb = teacherService.findCourseStuRank("0", totalCount, sid, username, sname, cname);
        pb.setExcel("courseStuRank");
        teacherService.infoExcel(pb, response);
    }

    //导出班级学生成绩Excel
    @RequestMapping("classStuRankInfo")
    public void classStuRankInfo(HttpServletResponse response, HttpSession session, String totalCount, String sname, String sid,String cname) {
        String username = (String) session.getAttribute("username");
        PageBean<Teacher> pb = teacherService.findClassStuRank("0", totalCount, sid, username, sname, cname);
        pb.setExcel("classStuRank");
        teacherService.infoExcel(pb, response);
    }

    //导出班级学生平均成绩Excel
    @RequestMapping("classStuAvgRankInfo")
    public void classStuAvgRankInfo(HttpServletResponse response, HttpSession session, String totalCount, String sname, String sid,String cname) {
        String username = (String) session.getAttribute("username");
        PageBean<Teacher> pb = teacherService.findClassStuAvgRank("0", totalCount, sid, username, sname);
        pb.setExcel("classStuAvgRank");
        teacherService.infoExcel(pb, response);
    }

    //导出班级学生总成绩Excel
    @RequestMapping("classStuSumRankInfo")
    public void classStuSumRankInfo(HttpServletResponse response, HttpSession session, String totalCount, String sname, String sid,String cname) {
        String username = (String) session.getAttribute("username");
        PageBean<Teacher> pb = teacherService.findClassStuSumRank("0", totalCount, sid, username, sname);
        pb.setExcel("classStuSumRank");
        teacherService.infoExcel(pb, response);
    }

    //跳转添加学生成绩页面
    @RequestMapping("toAddStuScore")
    public String toAddStuScore(HttpSession session,Model model) {
        String username = (String) session.getAttribute("username");
        List<Teacher> courses = teacherService.queryTeacherCourse(username);
        model.addAttribute("courses", courses);
        return "teacher/teacherAddCourseStu";
    }

    //添加学生成绩
    @RequestMapping("addStuScore")
    public String addStuScore(Model model,String sid,String cid,String score) {
        System.out.println("进入添加学生方法");
        if (sid==null||"".equals(sid)) sid = "0";
        if (cid==null||"notSelet".equals(cid)) cid = "0";
        System.out.println("sid:" + sid + ",cid:" + cid + ",score:" + score);
        Boolean result = teacherService.checkAddStu(sid, cid,score);
        if (result) {
            int s_id = Integer.parseInt(sid);
            int c_id = Integer.parseInt(cid);
            int s_score = Integer.parseInt(score);
            teacherService.addStuScore(s_id, c_id, s_score);
            return "redirect:/teacher/courseStuScore";
        } else {
            model.addAttribute("error_msg","信息错误或该学生已经存在成绩!");
            return "redirect:/teacher/toAddStuScore";
        }
    }

    //删除学生成绩
    @RequestMapping("deleteStuScoreById")
    public String deleteStuById(String sid, String cname) {
        System.out.println("sid改变前："+sid);
        int s_id = Integer.parseInt(sid);
        System.out.println("sid改变后："+s_id);
        teacherService.deleteStuById(s_id, cname);
        return "redirect:/teacher/courseStuScore";
    }

    //跳转修改学生成绩页面
    //@ResponseBody
    @RequestMapping("toUpdateStuScore")
    public String toUpdateStuScore(String sid,String sname,String cname,String score,String rank,String class_name, Model model) {
        System.out.println(sid + sname + cname + score + rank + class_name);
        model.addAttribute("sid", sid);
        model.addAttribute("sname", sname);
        model.addAttribute("cname", cname);
        model.addAttribute("score", score);
        model.addAttribute("rank", rank);
        model.addAttribute("class_name", class_name);
        return "teacher/teacherUpdateStuScore";
    }

    //修改学生成绩
    @RequestMapping("updateStuScore")
    public String updateStuScore(String sid,String sname,String cname,String score,String rank,String class_name, Model model) {
        int s_id = Integer.parseInt(sid);
        int s_score = -1;
        try {
            s_score = Integer.parseInt(score);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (s_score < 0 || s_score > 100) {
            //成绩无效，返回修改页面
            model.addAttribute("sid", sid);
            model.addAttribute("sname", sname);
            model.addAttribute("cname", cname);
            model.addAttribute("score", score);
            model.addAttribute("rank", rank);
            model.addAttribute("class_name", class_name);
            model.addAttribute("error_msg", "请输入有效成绩！");
            return "teacher/teacherUpdateStuScore";
        }
        teacherService.updateStuScore(s_id, cname, s_score);
        return "redirect:/teacher/courseStuScore";
    }

    //跳转修改密码页面
    @RequestMapping("toChangePsw")
    public String toChangePsw() {
        return "teacher/teacherChangePsw";
    }

    //修改密码，成功跳转登录页面，失败跳转修改页面
    @RequestMapping("changePsw")
    public String changePsw(HttpSession session, Model model,String oldPsw,String newPsw,String confirmPsw) {
        String username = (String) session.getAttribute("username");
        String psw = teacherService.teacherLoginCheck(username);
        if ("".equals(oldPsw) || oldPsw == null) {
            model.addAttribute("error_OldPsw", "   请输入正确格式！");
            return "teacher/teacherChangePsw";
        }
        if (newPsw == null || "".equals(newPsw) || confirmPsw == null || "".equals(confirmPsw)) {
            model.addAttribute("error_NewPsw", "   请输入正确格式！");
            return "teacher/teacherChangePsw";
        }
        //去除首位字符串
        System.out.println("newPsw:"+newPsw+" confirmPsw:"+confirmPsw);
        oldPsw = oldPsw.trim();
        newPsw = newPsw.trim();
        confirmPsw = confirmPsw.trim();
        System.out.println("newPsw:"+newPsw+" confirmPsw:"+confirmPsw);
        if (!psw.equals(oldPsw)) {
            model.addAttribute("error_OldPsw", "   旧密码错误！");
            return "teacher/teacherChangePsw";
        } else if (!newPsw.equals(confirmPsw)) {
            model.addAttribute("error_NewPsw", "   请输入相同的新密码！");
            return "teacher/teacherChangePsw";
        } else if (psw.equals(newPsw)) {
            model.addAttribute("error_NewPsw", "   新旧密码相同,请重新输入！");
            return "teacher/teacherChangePsw";
        } else if (newPsw.contains(" ")) {
            model.addAttribute("error_NewPsw", "   密码不能包含特殊字符！");
            return "teacher/teacherChangePsw";
        } else {
            teacherService.updateTeacherPsw(username, newPsw);
            model.addAttribute("login_msg", "密码修改请重新登录");
            session.removeAttribute("username");
            return "login";
        }
    }


    //退出登录
    @RequestMapping("exit")
    public String exit(HttpSession session) {
        session.removeAttribute("username");
        session.setAttribute("login_msg","退出成功");
        return "login";
    }

}
