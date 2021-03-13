package com.sun.dao;

import com.sun.pojo.Course;
import com.sun.pojo.Stu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StuMapper {

    //添加一名学生
    int addStu(Stu stu);

    //删除一名学生
    int deleteStu(@Param("s_id") int id);

    //修改学生信息
    int updateStu(Stu stu);

    //通过id查询一名学生
    Stu queryStuById(@Param("s_id") int id);

    //查询所有学生
    List<Stu> queryAllStu();

    //通过用户名查询学生个人信息
    Stu queryStuByUserName(@Param("username") String userName);

    //学生登录校验
    String stuLoginCheck(@Param("username")String userName);

    //通过用户名查询学生课程信息和成绩
    List<Stu> queryStuScoreCourse(@Param("username") String userName, @Param("cname") String c_name);

    //查询总平均分排行
    List<Stu> queryScoreRank(Map<String,Integer> map);

    //通过科目查询成绩排行
    List<Stu> queryScoreRankByCourse(Map map);

    //查询Score中的总记录数，通过班级
    int queryCountStu(@Param("class_id")int class_id);

    //查询Score中的总记录数，通过科目和班级
    int queryCountStuByCourse(@Param("cname") String c_name, @Param("class_id") int class_id);

    //修改密码
    int updateStuPsw(@Param("username") String userName,@Param("psw")String psw);

    //学生注册
    int stuRegistered(@Param("s_id") int id, @Param("username") String userName, @Param("psw") String psw);

    //查询成绩排行
    List<Stu> queryScoreByUserName(@Param("class_id")int class_id);


}
