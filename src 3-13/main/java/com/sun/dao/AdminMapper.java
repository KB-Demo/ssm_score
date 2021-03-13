package com.sun.dao;

import com.sun.pojo.*;
import org.apache.ibatis.annotations.Param;

import com.sun.pojo.Class;
import java.util.List;
import java.util.Map;

public interface AdminMapper {

    //管理员登录校验
    Admin adminLoginCheck(@Param("userName") String userName);

    //查找学生信息
    List<Stu> stuPerson(Map map);

    //查询学生总记录数
    int stuCount(Map map);

    //查询班级信息
    List<Class> stuClass();

    //添加学生
    int addStu(Stu stu);

    //修改学生信息
    int updateStu(Stu stu);

    //删除学生信息
    int deleteStuById(@Param("sid") int sid);
    int deleteScoreById(@Param("sid") int sid);
    int auto_Stu();//自动增长

    //通过学生用户名查找密码
    String queryPswByUserName(@Param("username") String userName);

    //查找教师信息
    List<Teacher> teacherPerson(Map map);

    //添加教师
    int addTeacher(Teacher teacher);

    //修改教师
    int updateTeacher(Teacher teacher);

    //删除教师
    int deleteTeacher(@Param("tid") int tid);
    int updateCourseTeacher(@Param("tid") int tid);
    int updateClassTeacher(@Param("tid") int tid);
    int auto_Teacher();//自动增长

    //通过教师用户名查找密码
    String queryPsw(@Param("username") String userName);

    //查询教师总记录数
    int teacherCount(Map map);

    //课程信息
    List<Course> teacherCourse(@Param("tname") String tname, @Param("cname") String cname);

    //添加课程
    int addCourse(@Param("cname") String cname, @Param("tid") int tid, @Param("credit") double credit);

    //查询教师id和姓名
    List<Teacher> queryTeacher();

    //查询是否有该课程名
    String queryCourseName(@Param("cname") String cname);

    //修改课程
    int updateCourse(Course course);

    //删除课程
    int deleteCourse(@Param("cid") int cid);
    int auto_Course();//自动增长

    //班级信息
    List<Class> teacherClass(@Param("class_name") String class_name, @Param("tname") String tname);

    //查询还未做班主任的教师
    List<Teacher> queryFreeTeacher();

    //查询班级
    String queryClassName(@Param("class_name") String class_name);

    //添加班级信息
    int addCLass(@Param("class_name") String class_name, @Param("tid") int tid, @Param("pid") int pid);

    //修改班级信息
    int updateClass(Class cl);

    //删除班级信息
    int deleteClass(@Param("class_id") int class_id);
    int auto_Class();

    //学生分数
    List<Score> stuScore(Map map);

    //查询分数总记录数
    int scoreCount(Map map);

    //班级平均分
    List<Class> classScore(@Param("class_name") String class_name, @Param("tname") String tname);

    //修改密码
    int updateAdminPsw(@Param("username") String userName, @Param("psw") String psw);

    /*三级联动*/
    //查找所有院系
    List<Faculty> findFaculty();

    //通过院系id查找所有专业
    List<Profession> findProfession(@Param("f_id") String f_id);

    //通过专业id查找所有班级
    List<Class> findClazz(@Param("p_id") String p_id);

    //通过班级ID查找专业
    Profession findProfessionByClazzID(@Param("class_id")int class_id);

    //通过专业ID查找院系
    Faculty findFacultyByProfessionID(@Param("p_id")int p_id);

}
