package com.sun.controller;

import com.sun.pojo.*;
import com.sun.pojo.Class;
import com.sun.service.AdminService;
import com.sun.utils.PageBean;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    /***************************************************** 学生信息页面 **********************************************************************/

    //跳转学生信息页面
    @RequestMapping("/stuPerson")
    public String stuPerson(Model model, String sname, String class_name, String currentPage, String rows, String sid) {
        if (sname==null) sname = "";
        if (class_name==null) class_name = "";
        if (currentPage==null) currentPage = "1";
        if (rows==null) rows = "10";
        if (sid==null || "".equals(sid)) sid = "0";
        PageBean<Stu> pb = adminService.adminStuPerson(sname, class_name, currentPage, rows, sid);
        List<Stu> stus = pb.getList();
        if (stus.size()==0) pb.setError("未查到该学生");
        model.addAttribute("pb", pb);
        model.addAttribute("stus", stus);
        model.addAttribute("class_name", class_name);
        model.addAttribute("sname", sname);
        model.addAttribute("sid", sid);
        return "admin/adminStuPerson";
    }

    //跳转添加学生页面
    @RequestMapping("/toAddStu")
    public String toAddStu(Model model) {
        /*List<Class> classes = adminService.stuClass();
        model.addAttribute("classes", classes);*/
        List<Faculty> faculty = adminService.findFaculty();
        model.addAttribute("faculty", faculty);
        return "admin/adminAddStu";
    }

    //添加学生页面,成功跳转学生信息页面，失败返回添加学生页面
    @RequestMapping("/addStu")
    public String addStu(Stu stu,@RequestParam("cl_id") String class_id,Model model) {
        if ("-1".equals(class_id)) {
            List<Faculty> faculty = adminService.findFaculty();
            model.addAttribute("faculty", faculty);
            model.addAttribute("error_class", "请填写正确班级信息");
            return "admin/adminAddStu";
        }
        int _class_id = Integer.parseInt(class_id);
        stu.setClass_id(_class_id);
        System.out.println(stu);
        String psw = adminService.queryPswByUserName(stu.getS_userName());
        if (psw!=null ) {
            List<Faculty> faculty = adminService.findFaculty();
            model.addAttribute("faculty", faculty);
            model.addAttribute("error_repeat", "已存在该用户名");
            return "admin/adminAddStu";
        }
        adminService.addStu(stu);
        return "redirect:/admin/stuPerson";
    }

    //选择院系后，通过院系查找专业
    @RequestMapping("/findProfession")
    @ResponseBody
    public List<Profession> findProfession(@RequestParam("f_id") String fid){
        System.out.println(fid+"接受到的ajax请求参数");
        List<Profession> profession = adminService.findProfession(fid);
        System.out.println(profession);
        return profession;
    }

    //选择专业后，通过专业选择班级
    @RequestMapping("/findClazz")
    @ResponseBody
    public List<Class> findClazz(@RequestParam("p_id") String pid){
        List<Class> clazz = adminService.findClazz(pid);
        System.out.println(clazz);
        return clazz;
    }

    //跳转修改学生页面
    @RequestMapping("/toUpdateStu")
    public String toUpdateStu(Model model, String s_id, String s_name, String s_birth, String s_sex, String s_tel, String s_email, String class_name, String class_id, String s_userName, String s_psw) {
       /* Profession profession = adminService.findProfessionByClazzID(Integer.parseInt(class_id));
        Faculty person_faculty = adminService.findFacultyByProfessionID(profession.getP_id());*/
        List<Faculty> faculty = adminService.findFaculty();
        /*Iterator<Faculty> iterator = faculty.iterator();
        while (iterator.hasNext()) {
            if (person_faculty.getF_name().equals(iterator.next().getF_name())) {
                iterator.remove();
            }
        }*/
        //System.out.println(person_faculty);
        model.addAttribute("s_id", s_id);
        model.addAttribute("s_name", s_name);
        model.addAttribute("s_birth", s_birth);
        model.addAttribute("s_sex", s_sex);
        model.addAttribute("s_tel", s_tel);
        model.addAttribute("s_email", s_email);
        List<Class> classes = adminService.stuClass();
        model.addAttribute("classes", classes);
        model.addAttribute("class_name", class_name);
        model.addAttribute("class_id", class_id);
        model.addAttribute("s_userName", s_userName);
        model.addAttribute("s_psw", s_psw);
        //model.addAttribute("profession", profession);
        model.addAttribute("faculty", faculty);
        //model.addAttribute("person_faculty", person_faculty);
        return "admin/adminUpdateStu";
    }

    //修改学生信息，成功跳转学生信息页面，失败返回修改学生信息页面
    @RequestMapping("/updateStu")
    public String updateStu(Model model, Stu stu, String class_name, @RequestParam("cl_id") String class_id, String s_id, String oldUserName) {
        int _class_id = Integer.parseInt(class_id);
        int _s_id = Integer.parseInt(s_id);
        stu.setS_id(_s_id);
        stu.setClass_id(_class_id);
        List<Faculty> faculty = adminService.findFaculty();
        /*Profession profession = adminService.findProfessionByClazzID(_class_id);
        Faculty faculty = adminService.findFacultyByProfessionID(profession.getP_id());
        List<Faculty> facultys = adminService.findFaculty();
        if (faculty.getF_name() !=null && !"".equals(faculty.getF_name())){
            Iterator<Faculty> iterator = facultys.iterator();
            while (iterator.hasNext()) {
                if ()
            }
        }*/
        System.out.println(stu);
        String psw = adminService.queryPswByUserName(stu.getS_userName());
        //如果修改后的用户名已存在
        if (psw != null && !stu.getS_userName().equals(oldUserName) || "-1".equals(class_id)) {
            model.addAttribute("error", "已存在该用户名或表格不完整");
            model.addAttribute("s_id", s_id);
            model.addAttribute("s_name", stu.getS_name());
            model.addAttribute("s_birth", stu.getS_birth());
            model.addAttribute("s_sex", stu.getS_sex());
            model.addAttribute("s_tel", stu.getS_tel());
            model.addAttribute("s_email", stu.getS_email());
            List<Class> classes = adminService.stuClass();
            model.addAttribute("classes", classes);
            model.addAttribute("class_name",class_name);
            model.addAttribute("class_id", class_id);
            model.addAttribute("s_userName", oldUserName);
            model.addAttribute("s_psw", stu.getS_psw());
            model.addAttribute("faculty", faculty);
           /* model.addAttribute("profession", profession);
            model.addAttribute("faculty", faculty);*/
            return "/admin/adminUpdateStu";
        }
        adminService.updateStu(stu);
        return "redirect:/admin/stuPerson";
    }

    //删除学生信息及成绩
    @RequestMapping("/deleteStu")
    public String deleteStu(String sid) {
        int _sid = Integer.parseInt(sid);
        adminService.deleteStu(_sid);
        return "redirect:/admin/stuPerson";
    }

    /***************************************************** 教师信息页面 **********************************************************************/

    //跳转教师信息页面
    @RequestMapping("/teacherPerson")
    public String teacherPerson(Model model, String tname, String tid, String currentPage, String rows) {
        if (tname == null) tname = "";
        if (currentPage == null) currentPage = "1";
        if (rows == null) rows = "10";
        if (tid == null || "".equals(tid)) tid = "0";
        PageBean<Teacher> pb = adminService.adminTeacherPerson(tname, tid, currentPage, rows);
        List<Teacher> teachers = pb.getList();
        if (teachers.size() == 0) pb.setError("未查到该教师");
        model.addAttribute("pb", pb);
        model.addAttribute("teachers", teachers);
        model.addAttribute("tname", tname);
        model.addAttribute("tid", tid);
        return "admin/adminTeacherPerson";
    }

    //跳转添加教师页面
    @RequestMapping("/toAddTeacher")
    public String toAddTeacher() {
        return "admin/adminAddTeacher";
    }

    //添加教师页面
    @RequestMapping("/addTeacher")
    public String addStu(Teacher teacher,Model model) {
        System.out.println(teacher);
    String psw = adminService.queryPsw(teacher.getT_userName());
        if (psw != null) {
        model.addAttribute("error", "已存在该用户名");
        return "admin/adminAddTeacher";
    }
        adminService.addTeacher(teacher);
        return "redirect:/admin/teacherPerson";
}

    //跳转修改教师页面
    @RequestMapping("/toUpdateTeacher")
    public String toUpdateTeacher(Model model, String t_id, String t_name, String t_birth, String t_sex, String t_tel, String t_email, String t_userName, String t_psw, String t_position, String t_education) {
        String[] positions = Position.getPositions();
        String[] educations = Education.getEducations();
        List<String> _positions = new ArrayList<>();
        List<String> _educations = new ArrayList<>();
        for (int i = 0; i < positions.length; i++) {
            if (!t_position.equals(positions[i])) {
                _positions.add(positions[i]);
            }
        }
        for (int i = 0; i < educations.length; i++) {
            if (!t_education.equals(educations[i])) {
                _educations.add(educations[i]);
            }
        }
        model.addAttribute("t_id", t_id);
        model.addAttribute("t_name", t_name);
        model.addAttribute("t_birth", t_birth);
        model.addAttribute("t_sex", t_sex);
        model.addAttribute("t_tel", t_tel);
        model.addAttribute("t_email", t_email);
        model.addAttribute("t_userName", t_userName);
        model.addAttribute("t_psw", t_psw);
        model.addAttribute("t_position", t_position);
        model.addAttribute("t_education", t_education);
        model.addAttribute("positions", _positions);
        model.addAttribute("educations", _educations);
        return "admin/adminUpdateTeacher";
    }

    //修改教师信息，成功跳转教师信息页面，失败返回修改教师信息页面
    @RequestMapping("/updateTeacher")
    public String updateTeacher(Teacher teacher, String t_id, Model model, String oldUserName) {
        int _t_id = Integer.parseInt(t_id);
        teacher.setT_id(_t_id);
        System.out.println(teacher);
        String psw = adminService.queryPsw(teacher.getT_userName());
        //如果修改后的用户名已存在
        if (psw != null && !oldUserName.equals(teacher.getT_userName())) {
            model.addAttribute("t_id", t_id);
            model.addAttribute("t_name", teacher.getT_name());
            model.addAttribute("t_birth", teacher.getT_birth());
            model.addAttribute("t_sex", teacher.getT_sex());
            model.addAttribute("t_tel", teacher.getT_tel());
            model.addAttribute("t_email", teacher.getT_email());
            model.addAttribute("t_userName", oldUserName);
            model.addAttribute("t_psw", teacher.getT_psw());
            model.addAttribute("error", "已存在该用户名");
            return "/admin/adminUpdateTeacher";
        }
        adminService.updateTeacher(teacher);
        return "redirect:/admin/teacherPerson";
    }

    //删除教师信息
    @RequestMapping("/deleteTeacher")
    public String deleteTeacher(String tid) {
        int _tid = Integer.parseInt(tid);
        adminService.deleteTeacher(_tid);
        return "redirect:/admin/teacherPerson";
    }

    /***************************************************** 课程信息页面 **********************************************************************/

    //跳转课程信息页面
    @RequestMapping("/courses")
    public String courses(Model model, String tname, String cname) {
        if (tname==null) tname = "";
        if (cname==null) cname = "";
        List<Course> courses = adminService.adminTeacherCourse(tname, cname);
        if (courses.size()==0) model.addAttribute("error", "未查到该课程");
        model.addAttribute("courses", courses);
        model.addAttribute("cname", cname);
        model.addAttribute("tname", tname);
        return "admin/adminCourses";
    }

    //跳转添加课程页面
    @RequestMapping("toAddCourse")
    public String toAddCourse(Model model) {
        List<Teacher> teachers = adminService.queryTeacher();
        model.addAttribute("teachers", teachers);
        return "admin/adminAddCourse";
    }

    //添加课程页面
    @RequestMapping("addCourse")
    public String addCourse(String tid, String c_name,String credit,Model model) {
        int t_id = Integer.parseInt(tid);
        String s = adminService.queryCourseName(c_name);
        double _credit = Double.parseDouble(credit);
        if (s != null) {//判断是否有该课程名
            model.addAttribute("error", "已存在该课程名");
            List<Teacher> teachers = adminService.queryTeacher();
            model.addAttribute("teachers", teachers);
            return "admin/adminAddCourse";
        }
        adminService.addCourse(c_name, t_id, _credit);
        return "redirect:/admin/courses";
    }

    //跳转修改课程页面
    @RequestMapping("/toUpdateCourse")
    public String toUpdateCourse(Model model, String c_id, String c_name, String t_id, String t_name, String credit) {
        model.addAttribute("c_id", c_id);
        model.addAttribute("c_name", c_name);
        model.addAttribute("t_id", t_id);
        model.addAttribute("t_name", t_name);
        model.addAttribute("credit", credit);
        List<Teacher> teachers = adminService.queryTeacher();
        Iterator<Teacher> iterator = teachers.iterator();
        if (!"".equals(t_id) && t_id != null) {
            while (iterator.hasNext()) {
                if (t_id.equals(String.valueOf(iterator.next().getT_id()))) {
                    iterator.remove();
                }
            }
        }

        model.addAttribute("teachers", teachers);
        return "admin/adminUpdateCourse";
    }

    @RequestMapping("/updateCourse")
    public String updateCourse(Model model,Course course,String oldCourseName,String oldTid,String c_id,String t_id,String oldTname,String credit) {
        course.setC_id(Integer.parseInt(c_id));
        course.setT_id(Integer.parseInt(t_id));
        course.setCredit(Double.parseDouble(credit));
        System.out.println(course);
        String s = adminService.queryCourseName(course.getC_name());
        if (s != null && !oldCourseName.equals(s)) {
            //判断是否有该课程名
            model.addAttribute("error", "已存在该课程名");
            model.addAttribute("c_id", c_id);
            model.addAttribute("c_name", oldCourseName);
            model.addAttribute("t_id", oldTid);
            model.addAttribute("t_name", oldTname);
            List<Teacher> teachers = adminService.queryTeacher();
            model.addAttribute("teachers", teachers);
            return "admin/adminUpdateCourse";
        }
        adminService.updateCourse(course);
        return "redirect:/admin/courses";
    }

    //删除课程信息
    @RequestMapping("/deleteCourse")
    public String deleteCourse(String c_id) {
        adminService.deleteCourse(Integer.parseInt(c_id));
        return "redirect:/admin/courses";
    }

    /***************************************************** 班级信息页面 **********************************************************************/

    //跳转班级信息页面
    @RequestMapping("/classes")
    public String classes(Model model, String class_name, String tname) {
        if (class_name == null) class_name = "";
        if (tname == null) tname = "";
        List<Class> classes = adminService.adminTeacherClass(class_name, tname);
        if (classes.size() == 0) model.addAttribute("error", "未查到该班级");
        model.addAttribute("classes", classes);
        model.addAttribute("class_name", class_name);
        model.addAttribute("tname", tname);
        return "admin/adminClasses";
    }

    //跳转添加班级页面
    @RequestMapping("/toAddClass")
    public String toAddClass(Model model) {
        List<Faculty> faculty = adminService.findFaculty();
        //查询不是班主任的老师
        List<Teacher> teachers = adminService.queryFreeTeacher();
        model.addAttribute("teachers", teachers);
        model.addAttribute("faculty", faculty);
        return "admin/adminAddClass";
    }

    //添加班级，成功返回班级信息页面，失败返回添加班级页面
    @RequestMapping("addClass")
    public String addClass(Model model, String class_name, String t_id,String p_id) {
        List<Faculty> faculty = adminService.findFaculty();
        List<Teacher> teachers = adminService.queryFreeTeacher();
        if ("-1".equals(p_id) || p_id == null) {
            model.addAttribute("faculty", faculty);
            model.addAttribute("teachers", teachers);
            model.addAttribute("error", "请选择专业院系");
            return "admin/adminAddClass";
        }
        //查询是否有该班级
        String check_name = adminService.queryClassName(class_name);
        if (check_name != null && check_name.equals(class_name)) {
            model.addAttribute("faculty", faculty);
            model.addAttribute("teachers", teachers);
            model.addAttribute("error", "已存在该班级名");
            return "admin/adminAddClass";
        }
        if (t_id == null || t_id == "") {
            model.addAttribute("faculty", faculty);
            model.addAttribute("teachers", teachers);
            model.addAttribute("error1", "无教师担任班主任");
            return "admin/adminAddClass";
        }
        adminService.addCLass(class_name, Integer.parseInt(t_id),Integer.parseInt(p_id));
        return "redirect:/admin/classes";
    }

    //跳转修改班级页面
    @RequestMapping("toUpdateClass")
    public String toUpdateClass(Model model, String class_name, String t_id, String t_name, String class_id) {
        model.addAttribute("class_name", class_name);
        model.addAttribute("t_id", t_id);
        model.addAttribute("t_name", t_name);
        model.addAttribute("class_id", class_id);
        List<Teacher> teachers = adminService.queryFreeTeacher();
        model.addAttribute("teachers", teachers);
        for (Teacher teacher : teachers) {
            System.out.println(teacher+"未做班主任的老师");
        }
        return "admin/adminUpdateClass";
    }

    //修改班级信息，成功返回班级信息页面，失败返回修改班级页面
    @RequestMapping("updateClass")
    public String updateClass(Class cl, String oldClassName, String oldTname, String oldTid, String class_id, String t_id, Model model) {
        //判断t_id是否为空
        try {
            Integer.parseInt(t_id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            model.addAttribute("class_name", oldClassName);
            model.addAttribute("t_id", oldTid);
            model.addAttribute("t_name", oldTname);
            model.addAttribute("class_id", class_id);
            List<Teacher> teachers = adminService.queryFreeTeacher();
            model.addAttribute("teachers", teachers);
            model.addAttribute("error1", "无教师担任班主任");
            return "admin/adminUpdateClass";
        }
        cl.setClass_id(Integer.parseInt(class_id));
        cl.setT_id(Integer.parseInt(t_id));
        System.out.println(cl);
        //查询是否有该班级
        String check_name = adminService.queryClassName(cl.getClass_name());
        if (check_name != null && !oldClassName.equals(check_name)) {
            model.addAttribute("class_name", oldClassName);
            model.addAttribute("t_id", oldTid);
            model.addAttribute("t_name", oldTname);
            model.addAttribute("class_id", class_id);
            List<Teacher> teachers = adminService.queryFreeTeacher();
            model.addAttribute("teachers", teachers);
            model.addAttribute("error", "已存在该班级名");
            return "admin/adminUpdateClass";
        }
        adminService.updateClass(cl);
        return "redirect:/admin/classes";
    }

    //删除班级信息
    @RequestMapping("deleteClass")
    public String deleteClass(String class_id) {
        adminService.deleteClass(Integer.parseInt(class_id));
        return "redirect:/admin/classes";
    }


    /***************************************************** 成绩报表页面 **********************************************************************/

    //学生成绩
    @RequestMapping("/stuScore")
    public String stuScore(Model model, String sname, String cname, String class_name, String currentPage, String rows) {
        if (class_name == null) class_name = "";
        if (cname == null) cname = "";
        if (sname == null) sname = "";
        if (currentPage == null) currentPage = "1";
        if (rows == null) rows = "10";
        PageBean<Score> pb = adminService.adminStuScore(sname, cname, class_name, currentPage, rows);
        List<Score> scores = pb.getList();
        if (scores.size() == 0) pb.setError("未查到该学生");
        model.addAttribute("pb", pb);
        model.addAttribute("scores", scores);
        model.addAttribute("cname", cname);
        model.addAttribute("sname", sname);
        model.addAttribute("class_name", class_name);
        return "admin/adminStuScore";
    }

    //班级平均分
    @RequestMapping("/classAvgScore")
    public String classAvgScore(Model model, String class_name, String tname) {
        if (class_name==null) class_name = "";
        if (tname==null) tname = "";
        List<Class> classes = adminService.adminClassScore(class_name, tname);
        if (classes.size()==0) model.addAttribute("error", "未查到该内容");
        model.addAttribute("classes", classes);
        model.addAttribute("class_name", class_name);
        model.addAttribute("tname", tname);
        return "admin/adminClassAvgScore";
    }

    /***************************************************** 导出Excel **********************************************************************/
    @RequestMapping("stuPersonInfo")
    public void stuPersonInfo(HttpServletResponse response, String totalCount, String sid, String sname, String class_name) {
        PageBean<Stu> pb = adminService.adminStuPerson(sname, class_name, "0", totalCount, sid);
        pb.setExcel("stuPerson");
        adminService.infoExcelByPageBean(response, pb);
    }

    @RequestMapping("teacherPersonInfo")
    public void teacherPersonInfo(HttpServletResponse response, String totalCount, String tid, String tname) {
        if (tid==null || tid=="") tid = "0";
        if (tname==null) tname = "";
        PageBean<Teacher> pb = adminService.adminTeacherPerson(tname, tid, "0", totalCount);
        pb.setExcel("teacherPerson");
        adminService.infoExcelByPageBean(response, pb);
    }

    @RequestMapping("stuScoreInfo")
    public void stuScoreInfo(HttpServletResponse response, String totalCount, String sname, String class_name, String cname) {
        if (sname==null) sname = "";
        if (class_name==null) class_name = "";
        if (cname==null) cname = "";
        PageBean<Score> pb = adminService.adminStuScore(sname, cname, class_name, "0", totalCount);
        pb.setExcel("stuScore");
        adminService.infoExcelByPageBean(response, pb);
    }

    @RequestMapping("coursesInfo")
    public void coursesInfo(HttpServletResponse response, String cname, String tname) {
        if (cname==null) cname = "";
        if (tname==null) tname = "";
        List<Course> courses = adminService.adminTeacherCourse(tname, cname);
        String excel_msg = "courses";
        adminService.infoExcelByList(response, courses, excel_msg);
    }

    @RequestMapping("classesInfo")
    public void classesInfo(HttpServletResponse response, String class_name, String tname) {
        if (tname==null) tname = "";
        if (class_name==null) class_name = "";
        List<Class> classes = adminService.adminTeacherClass(class_name, tname);
        String excel_msg = "classes";
        adminService.infoExcelByList(response, classes, excel_msg);
    }

    @RequestMapping("classAvgScoreInfo")
    public void classAvgScoreInfo(HttpServletResponse response, String tname, String class_name) {
        if (class_name==null) class_name = "";
        if (tname==null) tname = "";
        List<Class> classes = adminService.adminClassScore(class_name, tname);
        String excel_msg = "classAvgScore";
        adminService.infoExcelByList(response, classes, excel_msg);
    }

    /***************************************************** 其他业务 **********************************************************************/
    //跳转修改密码页面
    @RequestMapping("toChangePsw")
    public String toChangPsw() {
        return "admin/adminChangePsw";
    }

    //修改密码，成功跳转登录页面，失败跳转修改页面
    @RequestMapping("changePsw")
    public String ChangePsw(HttpSession session, Model model,String oldPsw,String newPsw,String confirmPsw) {
        String username = (String) session.getAttribute("username");
        Admin admin = adminService.adminLoginCheck(username);
        String psw = admin.getA_psw();
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
            return "admin/adminChangePsw";
        } else if (!newPsw.equals(confirmPsw)) {
            model.addAttribute("error_NewPsw", "   请输入相同的新密码！");
            return "admin/adminChangePsw";
        } else if (psw.equals(newPsw)) {
            model.addAttribute("error_NewPsw", "   新旧密码相同,请重新输入！");
            return "admin/adminChangePsw";
        } else if (newPsw.contains(" ")) {
            model.addAttribute("error_NewPsw", "   密码不能包含特殊字符！");
            return "admin/adminChangePsw";
        } else {
            adminService.updateAdminPsw(username, newPsw);
            model.addAttribute("login_msg", "密码修改请重新登录");
            session.removeAttribute("username");
            return "login";
        }
    }

    //退出登录
    @RequestMapping("exit")
    public String exit(HttpSession session) {
        session.removeAttribute("username");
        session.setAttribute("login_msg", "退出成功");
        return "login";
    }
}
