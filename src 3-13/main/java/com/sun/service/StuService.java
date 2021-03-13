package com.sun.service;

import com.sun.utils.PageBean;
import com.sun.pojo.Stu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StuService {
    //添加一名学生
    int addStu(Stu stu);

    //删除一名学生
    int deleteStu(int id);

    int updateStu(Stu stu);

    //通过id查询一名学生
    Stu queryStuById( int id);

    //查询所有学生
    List<Stu> queryAllStu();

    //学生登录校验
    String stuLoginCheck(String userName);

    //通过用户名查询学生个人信息
    Stu queryStuByUserName(@Param("username") String userName);

    //通过用户名查询学生课程信息和成绩
    List<Stu> queryStuScoreCourse( String userName,String c_name);

    //通过科目查询平均成绩排行
    //List<Stu> queryScoreRankByCourse(Map map);

    //分页查询过科目平均成绩排行
    PageBean<Stu> findCourseRankByPage(String _currentPage, String _rows, String c_name,Stu stu);

    //分页查询平均成绩
    PageBean<Stu> findRankByPage(String _currentPage, String _rows, Stu stu);

    //修改密码
    int updateStuPsw(String userName, String psw);

    //学生注册
    int stuRegistered(int id, String userName, String psw);

    //查询成绩排行
    List<Stu> queryScoreByUserName(int class_id);

    //通过科目查询平均成绩排行
    //List<Stu> queryScoreRankByCourse(Map map);

    //查询Score中的总记录数，通过科目分类
    //int queryCountStuByCourse(String c_name);

    //查询平均成绩排行
    //List<Stu> queryScoreRank(Map<String,Integer> map);

    //查询Score中的总记录数
    //int queryCountStu();
}
