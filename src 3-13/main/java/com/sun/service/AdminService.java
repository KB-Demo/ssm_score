package com.sun.service;

import com.sun.pojo.*;
import com.sun.pojo.Class;
import com.sun.utils.PageBean;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface AdminService {

    //管理员登录校验
    Admin adminLoginCheck(String userName);

    //查找学生信息
    PageBean<Stu> adminStuPerson(String sname, String class_name, String _currentPage, String _rows, String _sid);

    //查找教师信息
    PageBean<Teacher> adminTeacherPerson(String tname, String t_id, String _currentPage, String _rows);

    //课程信息
    List<Course> adminTeacherCourse(String tname, String cname);

    //班级信息
    List<Class> adminTeacherClass(String class_name, String tname);

    //学生分数
    PageBean<Score> adminStuScore(String sname, String cname, String class_name, String _currentPage, String _rows);

    //班级平均分
    List<Class> adminClassScore(String class_name, String tname);

    //查询班级信息
    List<Class> stuClass();

    //添加学生
    int addStu(Stu stu);

    //通过用户名查找密码
    String queryPswByUserName(String userName);

    //修改学生信息
    int updateStu(Stu stu);

    //删除学生信息
    int deleteStu(int sid);

    //添加教师
    int addTeacher(Teacher teacher);

    //修改教师
    int updateTeacher(Teacher teacher);

    //删除教师
    int deleteTeacher(int tid);

    //通过教师用户名查找密码
    String queryPsw(String userName);

    //添加课程
    int addCourse(String cname, int tid, double credit);

    //查询教师id和姓名
    List<Teacher> queryTeacher();

    //查询是否有该课程名
    String queryCourseName(String cname);

    //修改课程
    int updateCourse(Course course);

    //删除课程
    int deleteCourse(int cid);

    //查询还未做班主任的教师
    List<Teacher> queryFreeTeacher();

    //查询班级
    String queryClassName(String class_name);

    //添加班级信息
    int addCLass(String class_name, int tid, int pid);

    //修改班级信息
    int updateClass(Class cl);

    //删除班级信息
    int deleteClass(int class_id);

    //导出Excel,分页
    void infoExcelByPageBean(HttpServletResponse response, PageBean pb);

    //导出Excel，不分页
    void infoExcelByList(HttpServletResponse response, List list, String excel_msg);

    //修改密码
    int updateAdminPsw(String userName, String psw);

    /*三级联动*/
    //查找所有院系
    List<Faculty> findFaculty();

    //通过院系id查找所有专业
    List<Profession> findProfession(String f_id);

    //通过专业id查找所有班级
    List<Class> findClazz(String p_id);

    //通过班级ID查找专业
    Profession findProfessionByClazzID(int class_id);

    //通过专业ID查找院系
    Faculty findFacultyByProfessionID(int p_id);

}
