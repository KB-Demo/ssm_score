package com.sun.service;

import com.sun.dao.TeacherMapper;
import com.sun.utils.CreditUtil;
import com.sun.utils.TeacherExcelUtil;
import com.sun.utils.PageBean;
import com.sun.pojo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
    public int updateTeacherPsw(String userName, String newPsw) {
        return teacherMapper.updateTeacherPsw(userName, newPsw);
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
    public void CourseStuInfo() {

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
            pb.setError("未查到该学生");
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
        /*for (int i = 0; i < teachers.size(); i++) {
            teachers.get(i).getCourse().setCredit(CreditUtil.pointOfEachCourse(teachers.get(i).getCourse().getCredit(),teachers.get(i).getScore().getS_score()));
        }*/
        //5.1计算学分
        for (Teacher teacher : teachers) {
            teacher.getCourse().setCredit(CreditUtil.pointOfEachCourse(teacher.getCourse().getCredit(),teacher.getScore().getS_score()));
        }
        //6.设置参数到pb
        pb.setList(teachers);
        pb.setCurrentPage(currentPage);
        pb.setTotalCount(totalCount);
        pb.setTotalPage(totalPage);
        pb.setRows(rows);
        return pb;
    }

    @Override
    public PageBean<Teacher> findClassStuRank(String _currentPage, String _rows, String _sid, String t_userName, String s_name, String c_name) {
        //1.创建PageBean对象
        PageBean<Teacher> pb = new PageBean<>();
        //2.设置参数
        int rows = Integer.parseInt(_rows);
        int currentPage = Integer.parseInt(_currentPage);
        int sid = 0;
        try {
            sid = Integer.parseInt(_sid);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            pb.setError("未查到该学生");
        }
        //3.调用dao计算总记录数
        Map map = new HashMap();
        map.put("username", t_userName);
        map.put("sid", sid);
        map.put("sname", s_name);
        map.put("cname", c_name);
        int totalCount = teacherMapper.queryClassStuRankCount(map);
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
        List<Teacher> teachers = teacherMapper.queryTeacherClassStuRank(map);
        //5.1计算学分
        for (Teacher teacher : teachers) {
            teacher.getCourse().setCredit(CreditUtil.pointOfEachCourse(teacher.getCourse().getCredit(),teacher.getScore().getS_score()));
        }
        //6.设置参数到pb
        pb.setTotalPage(totalPage);
        pb.setTotalCount(totalCount);
        pb.setCurrentPage(currentPage);
        pb.setList(teachers);
        pb.setRows(rows);
        return pb;
    }

    @Override
    public PageBean<Teacher> findClassStuAvgRank(String _currentPage, String _rows, String _sid, String t_userName, String s_name) {
        //1.创建PageBean对象\
        PageBean<Teacher> pb = new PageBean<>();
        //2.设置参数
        int currentPage = Integer.parseInt(_currentPage);
        int rows = Integer.parseInt(_rows);
        int sid = 0;
        try {
            sid = Integer.parseInt(_sid);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            pb.setError("未查到该学生");
        }
        //3.调用dao查询总记录数
        Map map = new HashMap();
        map.put("username", t_userName);
        map.put("sname", s_name);
        map.put("sid", sid);
        int totalCount = teacherMapper.queryClassStuCount(map);
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
        List<Teacher> teachers = teacherMapper.queryTeacherClassStuAvgRank(map);
        //6.设置参数到pb中
        pb.setRows(rows);
        pb.setList(teachers);
        pb.setCurrentPage(currentPage);
        pb.setTotalCount(totalCount);
        pb.setTotalPage(totalPage);
        return pb;
    }

    @Override
    public PageBean<Teacher> findClassStuSumRank(String _currentPage, String _rows, String _sid, String t_userName, String s_name) {
        //1.创建PageBean对象\
        PageBean<Teacher> pb = new PageBean<>();
        //2.设置参数
        int currentPage = Integer.parseInt(_currentPage);
        int rows = Integer.parseInt(_rows);
        int sid = 0;
        try {
            sid = Integer.parseInt(_sid);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            pb.setError("未查到该学生");
        }
        //3.调用dao查询总记录数
        Map map = new HashMap();
        map.put("username", t_userName);
        map.put("sname", s_name);
        map.put("sid", sid);
        int totalCount = teacherMapper.queryClassStuCount(map);
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
        List<Teacher> teachers = teacherMapper.queryTeacherClassStuSumRank(map);
        //6.设置参数到pb中
        pb.setRows(rows);
        pb.setList(teachers);
        pb.setCurrentPage(currentPage);
        pb.setTotalCount(totalCount);
        pb.setTotalPage(totalPage);
        return pb;
    }

    @Override
    public List<Teacher> queryTeacherCourse(String userName) {
        return teacherMapper.queryTeacherCourse(userName);
    }

    @Override
    public int addStuScore(int sid, int cid, int score) {
        int check;
        Lock lock = new ReentrantLock();
        try {
            lock.lock();
            check = teacherMapper.addStuScore(sid, cid, score);
        } finally {
            lock.unlock();
        }
        return check;
    }

    @Override
    public Boolean checkAddStu(String _sid, String _cid, String _score) {
        int sid = stringToInt(_sid);
        int cid = stringToInt(_cid);
        int score = stringToInt(_score);
        int a = teacherMapper.checkScore(sid, cid);
        int b = teacherMapper.queryStuById(sid);
        System.out.println("a:" + a + ",b:" + b);
        if (a == 0 && score <= 100 && score >= 0 && b == 1) {
            return true;
        } else return false;
    }

    @Override
    public int deleteStuById(int sid, String cname) {
        int check;
        Lock lock = new ReentrantLock();
        try {
            lock.lock();
            check = teacherMapper.deleteStuById(sid, cname);
        } finally {
            lock.unlock();
        }
        return check;
    }

    @Override
    public int updateStuScore(int sid, String cname, int score) {
        int check;
        Lock lock = new ReentrantLock();
        try {
            lock.lock();
            check = teacherMapper.updateStuScore(sid, cname, score);
        } finally {
            lock.unlock();
        }
        return check;
    }

    @Override
    public void infoExcel(PageBean pb, HttpServletResponse response) {
        TeacherExcelUtil teacherExcelUtil = new TeacherExcelUtil();
        try {
            teacherExcelUtil.InfoExcel(pb, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int stringToInt(String str) {
        int n = 0;
        try {
            n = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return n;
    }

}
