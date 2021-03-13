package com.sun.service;

import com.sun.dao.AdminMapper;
import com.sun.pojo.*;
import com.sun.pojo.Class;
import com.sun.utils.AdminExcelUtil;
import com.sun.utils.CreditUtil;
import com.sun.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;


    @Override
    public Admin adminLoginCheck(String userName) {
        return adminMapper.adminLoginCheck(userName);
    }

    @Override
    public PageBean<Stu> adminStuPerson(String sname, String class_name, String _currentPage, String _rows, String _sid) {
        //1.创建PageBean对象
        PageBean<Stu> pb = new PageBean<>();
        //2.设置参数
        int currentPage = stringToInt(_currentPage);
        int rows = stringToInt(_rows);
        int sid = 0;
        try {
            sid = Integer.parseInt(_sid);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            pb.setError("未查到该学生");
        }
        //3.调用dao查询总记录数
        Map map = new HashMap<>();
        map.put("sid", sid);
        map.put("sname", sname);
        map.put("class_name", class_name);
        int totalCount = adminMapper.stuCount(map);
        System.out.println(totalCount);
        //4.计算总页码
        int totalPage = totalCount % rows == 0 ? totalCount / rows : totalCount / rows + 1;
        //4.1防止越界
        if (totalPage==0) totalPage = 1;
        if (currentPage<1) currentPage = 1;
        if (currentPage>totalPage) currentPage = totalPage;
        //4.2设置开始索引
        int startIndex = (currentPage - 1) * rows;
        //5.调用dao计算每页数据
        map.put("startIndex", startIndex);
        map.put("rows", rows);
        List<Stu> stus = adminMapper.stuPerson(map);
        //6.设置参数到pb
        pb.setTotalPage(totalPage);
        pb.setTotalCount(totalCount);
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);
        pb.setList(stus);
        return pb;
    }

    @Override
    public PageBean<Teacher> adminTeacherPerson(String tname, String t_id, String _currentPage, String _rows) {
        //1.创建PageBean对象
        PageBean<Teacher> pb = new PageBean<>();
        //2.设置参数
        int currentPage = stringToInt(_currentPage);
        int rows = stringToInt(_rows);
        int tid = 0;
        try {
            tid = Integer.parseInt(t_id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            pb.setError("未查到该学号教师");
        }
        //3.调用dao查找总记录数
        Map map = new HashMap<>();
        map.put("tid", tid);
        map.put("tname", tname);
        int totalCount = adminMapper.teacherCount(map);
        //4.计算总页码
        int totalPage = totalCount % rows == 0 ? totalCount / rows : totalCount / rows + 1;
        //4.1防止越界
        if (totalPage == 0) totalPage = 1;
        if (currentPage<1) currentPage = 1;
        if (currentPage>totalPage) currentPage = totalPage;
        //4.2计算开始索引startIndex
        int startIndex = (currentPage - 1) * rows;
        //5.调用dao计算每页数据
        map.put("startIndex", startIndex);
        map.put("rows", rows);
        List<Teacher> teachers = adminMapper.teacherPerson(map);
        List<Teacher> check_teachers = new ArrayList<>(20);
        System.out.println("tid:"+t_id+",tname:"+tname);
        for (Teacher teacher : teachers) {
            //没有查询条件时
            if (tname.equals("") && t_id.equals("0")) {
                check_teachers.add(teacher);
                System.out.println("1");
                continue;
            } else if (tname.equals("") && !t_id.equals("0")) {//查询条件为教师号时
                if (teacher.getT_id() == tid) {
                    check_teachers.add(teacher);
                    System.out.println("2");
                    continue;
                } else {
                    System.out.println("3");
                    continue;
                }
            } else if (!tname.equals("") && t_id.equals("0")) {//查询条件为教师姓名时
                if (teacher.getT_name().contains(tname)) {
                    System.out.println("4");
                    check_teachers.add(teacher);
                    continue;
                } else {
                    System.out.println("5");
                    continue;
                }
            } else {//查询条件为两者时
                if (teacher.getT_id() == tid && teacher.getT_name().contains(tname)) {
                    check_teachers.add(teacher);
                    System.out.println("6");
                    continue;
                } else {
                    System.out.println("7");
                    continue;
                }
            }
        }
        for (Teacher check_teacher : check_teachers) {
            System.out.println(check_teacher);
        }
        //6.设置参数到pb
        pb.setList(check_teachers);
        pb.setRows(rows);
        pb.setCurrentPage(currentPage);
        pb.setTotalCount(totalCount);
        pb.setTotalPage(totalPage);
        return pb;
    }

    @Override
    public List<Course> adminTeacherCourse(String tname, String cname) {
        List<Course> courses = adminMapper.teacherCourse(tname, cname);
        List<Course> check_courses = new ArrayList<>(20);
        for (Course cours : courses) {
            if ("".equals(tname) && "".equals(cname)) {//没有查询条件时
                check_courses.add(cours);
                continue;
            } else if ("".equals(tname) && !"".equals(cname)) {//查询条件为cname
                if (cours.getC_name().contains(cname)) {
                    check_courses.add(cours);
                    continue;
                } else {
                    continue;
                }
            } else if (!"".equals(tname) && "".equals(cname)) {//查询条件为tname
                if (cours.getTeacher().getT_name()!=null) {//因为是左外连接查询并且sql语句查询的结果为*，所有只要cours内teacher的t_name不为null就代表要查找的结果
                    check_courses.add(cours);
                    continue;
                } else {
                    continue;
                }
            } else {//查询条件为两者时
                if (cours.getTeacher().getT_name()!=null && cours.getC_name().contains(cname)) {
                    check_courses.add(cours);
                    continue;
                } else {
                    continue;
                }
            }
        }
        return check_courses;
    }

    @Override
    public List<Class> adminTeacherClass(String class_name, String tname) {
        List<Class> classes = adminMapper.teacherClass(class_name, tname);
        List<Class> check_classes = new ArrayList<>(20);
        for (Class aClass : classes) {
            if ("".equals(tname) && "".equals(class_name)) {//没有查询条件时
                check_classes.add(aClass);
                continue;
            } else if ("".equals(tname) && !"".equals(class_name)) {//查询条件为class_name
                if (aClass.getClass_name().contains(class_name)) {
                    check_classes.add(aClass);
                    continue;
                } else {
                    continue;
                }
            } else if (!"".equals(tname) && "".equals(class_name)) {//查询条件为tname
                if (aClass.getTeacher()!=null) {//因为sql查询的结果是t_id和t_name，所以只要查不出这两个条件就不会封装teacher，
                    check_classes.add(aClass);  // 所以只要封装了teacher的就为想要的结果，可以和上一条的sql优化
                    continue;
                } else {
                    continue;
                }
            } else {//查询条件为两者时
                    if (aClass.getTeacher()!=null && aClass.getClass_name().contains(class_name)) {
                        check_classes.add(aClass);
                        continue;
                } else {
                    continue;
                }
            }
        }
        return check_classes;
    }

    @Override
    public PageBean<Score> adminStuScore(String sname, String cname, String class_name, String _currentPage, String _rows) {
        //1.创建PageBean对象
        PageBean<Score> pb = new PageBean<>();
        //2.设置参数
        int currentPage = stringToInt(_currentPage);
        int rows = stringToInt(_rows);
        //3.调用dao计算总记录数
        Map map = new HashMap<>();
        map.put("cname", cname);
        map.put("sname", sname);
        map.put("class_name", class_name);
        int totalCount = adminMapper.scoreCount(map);
        //4.计算总页码
        int totalPage = totalCount % rows == 0 ? totalCount / rows : totalCount / rows + 1;
        //4.1防止越界
        if (totalPage==0) totalPage = 1;
        if (currentPage<1) currentPage = 1;
        if (currentPage > totalPage) currentPage = totalPage;
        //4.2计算开始索引startIndex
        int startIndex = (currentPage - 1) * rows;
        //5.调用dao计算每页数据
        map.put("startIndex", startIndex);
        map.put("rows", rows);
        List<Score> scores = adminMapper.stuScore(map);
        //5.1查询学分 //5.2记录排行名。。。
        Lock lock = new ReentrantLock();
        /*Map map_bug = new HashMap();
        map_bug.put("cname", "");
        map_bug.put("sname", "");
        map_bug.put("class_name", "");
        map.put("startIndex", 1);
        map.put("rows", 10);
        List<Score> scores_bug = adminMapper.stuScore(map_bug);*/
        try {
            lock.lock();
            for (Score score : scores) {
                score.getCourse().setCredit(CreditUtil.pointOfEachCourse(score.getCourse().getCredit(),score.getS_score()));//设置学分
            }
        }finally {
            lock.unlock();
        }

        //6.设置参数到pb
        pb.setList(scores);
        pb.setRows(rows);
        pb.setCurrentPage(currentPage);
        pb.setTotalCount(totalCount);
        pb.setTotalPage(totalPage);
        return pb;
    }

    @Override
    public List<Class> adminClassScore(String class_name, String tname) {
        return adminMapper.classScore(class_name, tname);
    }

    @Override
    public List<Class> stuClass() {
        return adminMapper.stuClass();
    }

    @Override
    public int addStu(Stu stu) {
        ReentrantLock lock = new ReentrantLock();
        int check;
        try{
            lock.lock();
            check = adminMapper.addStu(stu);
        }finally {
            lock.unlock();
        }
        return check;
    }

    @Override
    public String queryPswByUserName(String userName) {
        return adminMapper.queryPswByUserName(userName);
    }

    @Override
    public int updateStu(Stu stu) {
        ReentrantLock lock = new ReentrantLock();
        int check;
        try{
            lock.lock();
            check = adminMapper.updateStu(stu);
        }finally {
            lock.unlock();
        }
        return check;
    }

    @Override
    public int deleteStu(int sid) {
        ReentrantLock lock = new ReentrantLock();
        int check;
        try{
            lock.lock();
            check = adminMapper.deleteScoreById(sid)+adminMapper.deleteStuById(sid)+adminMapper.auto_Stu();
        }finally {
            lock.unlock();
        }
        return check;
    }

    @Override
    public int addTeacher(Teacher teacher) {
        ReentrantLock lock = new ReentrantLock();
        int check;
        try{
            lock.lock();
            check = adminMapper.addTeacher(teacher);
        }finally {
            lock.unlock();
        }
        return check;
    }

    @Override
    public int updateTeacher(Teacher teacher) {
        ReentrantLock lock = new ReentrantLock();
        int check;
        try{
            lock.lock();
            check = adminMapper.updateTeacher(teacher);
        }finally {
            lock.unlock();
        }
        return check;
    }

    @Override
    public int deleteTeacher(int tid) {
        ReentrantLock lock = new ReentrantLock();
        int check;
        try{
            lock.lock();
            check = adminMapper.deleteTeacher(tid) + adminMapper.updateClassTeacher(tid) + adminMapper.updateCourseTeacher(tid)+adminMapper.auto_Teacher();
        }finally {
            lock.unlock();
        }
        return check;
    }

    @Override
    public String queryPsw(String userName) {
        return adminMapper.queryPsw(userName);
    }

    @Override
    public int addCourse(String cname, int tid, double credit) {
        ReentrantLock lock = new ReentrantLock();
        int check;
        try{
            lock.lock();
            check = adminMapper.addCourse(cname, tid, credit);
        }finally {
            lock.unlock();
        }
        return check;
    }

    @Override
    public List<Teacher> queryTeacher() {
        return adminMapper.queryTeacher();
    }

    @Override
    public String queryCourseName(String cname) {
        return adminMapper.queryCourseName(cname);
    }

    @Override
    public int updateCourse(Course course) {
        ReentrantLock lock = new ReentrantLock();
        int check;
        try{
            lock.lock();
            check = adminMapper.updateCourse(course);
        }finally {
            lock.unlock();
        }
        return check;
    }

    @Override
    public int deleteCourse(int cid) {
        ReentrantLock lock = new ReentrantLock();
        int check;
        try{
            lock.lock();
            check = adminMapper.deleteCourse(cid)+adminMapper.auto_Course();
        }finally {
            lock.unlock();
        }
        return check;
    }

    @Override
    public List<Teacher> queryFreeTeacher() {
        return adminMapper.queryFreeTeacher();
    }

    @Override
    public String queryClassName(String class_name) {
        return adminMapper.queryClassName(class_name);
    }

    @Override
    public int addCLass(String class_name, int tid, int pid) {
        ReentrantLock lock = new ReentrantLock();
        int check;
        try {
            lock.lock();
            check = adminMapper.addCLass(class_name, tid, pid);
        } finally {
            lock.unlock();
        }
        return check;
    }

    @Override
    public int updateClass(Class cl) {
        ReentrantLock lock = new ReentrantLock();
        int check;
        try{
            lock.lock();
            check = adminMapper.updateClass(cl);
        }finally {
            lock.unlock();
        }
        return check;
    }

    @Override
    public int deleteClass(int class_id) {
        ReentrantLock lock = new ReentrantLock();
        int check;
        try{
            lock.lock();
            check = adminMapper.deleteClass(class_id)+adminMapper.auto_Class();
        }finally {
            lock.unlock();
        }
        return check;
    }

    @Override
    public void infoExcelByPageBean(HttpServletResponse response, PageBean pb) {
        AdminExcelUtil adminExcelUtil = new AdminExcelUtil();
        try {
            adminExcelUtil.InfoExcelByPageBean(response, pb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void infoExcelByList(HttpServletResponse response, List list, String excel_msg) {
        try {
            new AdminExcelUtil().InfoExcelByList(response, list, excel_msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int updateAdminPsw(String userName, String psw) {
        return adminMapper.updateAdminPsw(userName, psw);
    }

    @Override
    public List<Faculty> findFaculty() {
        return adminMapper.findFaculty();
    }

    @Override
    public List<Profession> findProfession(String f_id) {
        return adminMapper.findProfession(f_id);
    }

    @Override
    public List<Class> findClazz(String p_id) {
        return adminMapper.findClazz(p_id);
    }

    @Override
    public Profession findProfessionByClazzID(int class_id) {
        return adminMapper.findProfessionByClazzID(class_id);
    }

    @Override
    public Faculty findFacultyByProfessionID(int p_id) {
        return adminMapper.findFacultyByProfessionID(p_id);
    }

    public int stringToInt(String str) {
        int a = 0;
        try {
            a = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return a;
    }

}
