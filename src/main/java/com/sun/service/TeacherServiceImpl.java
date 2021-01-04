package com.sun.service;

import com.sun.dao.TeacherMapper;
import com.sun.pojo.PageBean;
import com.sun.pojo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherMapper teacherMapper;

    @Override
    public String teacherLoginCheck(String userName) {
        return teacherMapper.teacherLoginCheck(userName);
    }

    @Override
    public Teacher queryTeacherById(int id) {
        return teacherMapper.queryTeacherById(id);
    }

    @Override
    public int teacherRegistered(int id, String userName, String psw) {
        return teacherMapper.teacherRegistered(id, userName, psw);
    }

    @Override
    public List<Teacher> queryCourse(String userName) {
        return teacherMapper.queryCourse(userName);
    }

    @Override
    public Teacher queryTeacherPersonal(String userName) {
        return teacherMapper.queryTeacherPersonal(userName);
    }

    @Override
    public int updateTeacher(Teacher teacher) {
        return teacherMapper.updateTeacher(teacher);
    }

    @Override
    public PageBean<Teacher> findCourseStu(String _currentPage, String _rows, String _sid, String t_userName, String s_name) {
        //1.创建空参PageBean对象
        PageBean<Teacher> pb = new PageBean<>();
        //2.设置参数
        int currentPage = Integer.parseInt(_currentPage);
        int rows = Integer.parseInt(_rows);
        int sid = 0;
        try {
            sid = Integer.parseInt(_sid);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            pb.setError("未查到该内容学生");
        }
        //3.调用dao查询总记录数
        Map map = new HashMap();
        map.put("username", t_userName);
        map.put("sid", sid);
        map.put("sname", s_name);
        map.put("cname", "");
        int totalCount = teacherMapper.queryCourseStuCount(map);
        //4.计算总页码
        int totalPage = totalCount % rows == 0 ? totalCount / rows : totalCount / rows + 1;
        //4.1防止越界
        if (totalPage == 0) {
            totalPage = 1;
            pb.setError("未查到该内容学生");
        }
        if (currentPage < 1) currentPage = 1;
        if (currentPage > totalPage) currentPage = totalPage;
        //4.2设置开始索引startIndex
        int startIndex = (currentPage - 1) * rows;
        //5.调用dao查询数据
        map.put("startIndex", startIndex);
        map.put("rows", rows);
        List<Teacher> teachers = teacherMapper.queryTeacherCourseStu(map);
        //6.设置参数到pb中
        pb.setList(teachers);
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);
        pb.setTotalCount(totalCount);
        pb.setTotalPage(totalPage);
        return pb;
    }

    @Override
    public PageBean<Teacher> findClassStu(String _currentPage, String _rows, String _sid, String t_userName, String s_name) {
        //1.创建空参PageBean对象
        PageBean<Teacher> pb = new PageBean<>();
        //2.设置参数
        int currentPage = Integer.parseInt(_currentPage);
        int rows = Integer.parseInt(_rows);
        int sid = 0;
        try {
            sid = Integer.parseInt(_sid);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            pb.setError("未查到该内容学生");
        }
        //3.调用dao计算总记录数
        Map map = new HashMap();
        map.put("username", t_userName);
        map.put("sname", s_name);
        map.put("sid", sid);
        int totalCount = teacherMapper.queryClassStuCount(map);
        //4.计算总页码
        int totalPage = totalCount % rows == 0 ? totalCount / rows : totalCount / rows + 1;
        //4.1防止越界
        if (totalPage == 0){
            totalPage = 1;
            pb.setError("未查到该内容学生");
        }
        if (currentPage <1) currentPage = 1;
        if (currentPage > totalPage) currentPage = totalPage;
        //4.2计算开始索引 startIndex=(currentPage-1)*rows
        int startIndex = (currentPage - 1) * rows;
        //5.调用dao查询数据
        map.put("startIndex", startIndex);
        map.put("rows", rows);
        List<Teacher> teachers = teacherMapper.queryTeacherClassStu(map);
        //6.设置参数到pb中
        pb.setTotalPage(totalPage);
        pb.setTotalCount(totalCount);
        pb.setRows(rows);
        pb.setCurrentPage(currentPage);
        pb.setList(teachers);
        return pb;
    }

    @Override
    public PageBean<Teacher> findCourseStuRank(String _currentPage, String _rows, String _sid, String t_userName, String s_name, String c_name) {
        //1.创建PageBean对象
        PageBean<Teacher> pb = new PageBean<>();
        //2.设置参数
        int currentPage = Integer.parseInt(_currentPage);
        int rows = Integer.parseInt(_rows);
        int sid = 0;
        try {
            sid = Integer.parseInt(_sid);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            pb.setError("为查到该学生");
        }
        //3.调用dao查询总记录数
        Map map = new HashMap();
        map.put("sid", sid);
        map.put("username", t_userName);
        map.put("sname", s_name);
        map.put("cname", c_name);
        int totalCount = teacherMapper.queryCourseStuCount(map);
        //4.计算总页码
        int totalPage = totalCount % rows == 0 ? totalCount / rows : totalCount / rows + 1;
        //4.1防止越界
        if (totalPage==0) totalPage = 1;
        if (currentPage<1) currentPage = 1;
        if (currentPage>totalPage) currentPage = totalPage;
        //4.2设置开始索引startIndex
        int startIndex = (currentPage - 1) * rows;
        //5.调用dao计算每页数据
        map.put("startIndex", startIndex);
        map.put("rows", rows);
        List<Teacher> teachers = teacherMapper.queryTeacherCourseStuRank(map);
        //6.设置参数到pb
        pb.setList(teachers);
        pb.setCurrentPage(currentPage);
        pb.setTotalCount(totalCount);
        pb.setTotalPage(totalPage);
        return pb;
    }

}
