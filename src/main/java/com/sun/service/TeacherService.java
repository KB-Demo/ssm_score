package com.sun.service;

import com.sun.pojo.PageBean;
import com.sun.pojo.Stu;
import com.sun.pojo.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherService {

    //教师登录校验
    String teacherLoginCheck(String userName);

    //通过ID查找教师
    Teacher queryTeacherById(int id);

    //教师用户注册
    int teacherRegistered(int id, String userName, String psw);

    //通过教师用户名查询教师信息
    //Teacher queryTeacherByUserName(String userName);

    //查询教师课程信息
    List<Teacher> queryCourse(String userName);

    //查询教师个人信息
    Teacher queryTeacherPersonal(String userName);

    //修改教师信息
    int updateTeacher(Teacher teacher);

    //查询老师课程的学生
    PageBean<Teacher> findCourseStu(String _currentPage, String _rows, String _sid, String t_userName, String s_name);

    //查询老师带领班级的学生
    PageBean<Teacher> findClassStu(String _currentPage, String _rows, String _sid, String t_userName, String s_name);

    //查询老师课程学生的成绩排行
    PageBean<Teacher> findCourseStuRank(String _currentPage, String _rows, String _sid, String t_userName, String s_name, String c_name);

}
