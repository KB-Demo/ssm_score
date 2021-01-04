package com.sun.dao;

import com.sun.pojo.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TeacherMapper {

    //教师登录校验
    String teacherLoginCheck(@Param("userName") String userName);

    //通过ID查找教师
    Teacher queryTeacherById(@Param("t_id") int id);

    //教师用户注册
    int teacherRegistered(@Param("t_id") int id, @Param("username") String userName, @Param("psw") String psw);

    //通过教师用户名查询教师信息
    //Teacher queryTeacherByUserName(@Param("username") String userName);

    //查询教师课程信息
    List<Teacher> queryCourse(@Param("username") String userName);

    //通过教师用户名查询教师个人信息
    Teacher queryTeacherPersonal(@Param("username") String userName);

    //修改教师信息
    int updateTeacher(Teacher teacher);

    //查询老师课程的学生信息,参数:教师username,学生sname,学生sid,开始页码startIndex,每页条数rows
    List<Teacher> queryTeacherCourseStu(Map map);

    //查询老师课程学生的总记录数
    int queryCourseStuCount(Map map);

    //查询老师带领班级的学生信息，参数:教师username,学生sname,学生sid,开始页码startIndex,每页条数rows
    List<Teacher> queryTeacherClassStu(Map map);

    //查询老师带领班级学生的总记录数
    int queryClassStuCount(Map map);

    //查询老师带领班级的学生成绩排行
    List<Teacher> queryTeacherCourseStuRank(Map map);
}