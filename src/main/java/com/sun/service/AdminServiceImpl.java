package com.sun.service;

import com.sun.dao.AdminMapper;
import com.sun.pojo.*;
import com.sun.pojo.Class;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;


    @Override
    public Admin adminLoginCheck(String userName) {
        return adminMapper.adminLoginCheck(userName);
    }

    @Override
    public PageBean<Stu> adminStuPerson(String sname, String class_name, String _currentPage, String _rows,String _sid) {
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
        int totalCount = adminMapper.stuCount();
        //4.计算总页码
        int totalPage = totalCount % rows == 0 ? totalCount / rows : totalCount / rows + 1;
        //4.1防止越界
        if (totalPage==0) totalPage = 1;
        if (currentPage<1) currentPage = 1;
        if (currentPage>totalPage) currentPage = totalPage;
        //4.2设置开始索引
        int startIndex = (currentPage - 1) * rows;
        //5.调用dao计算每页数据
        Map map = new HashMap<>();
        map.put("startIndex", startIndex);
        map.put("rows", rows);
        map.put("sid", sid);
        map.put("sname", sname);
        map.put("class_name", class_name);
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
        int totalCount = adminMapper.teacherCount();
        //4.计算总页码
        int totalPage = totalCount % rows == 0 ? totalCount / rows : totalCount / rows + 1;
        //4.1防止越界
        if (totalPage == 0) totalPage = 1;
        if (currentPage<1) currentPage = 1;
        if (currentPage>totalPage) currentPage = totalPage;
        //4.2计算开始索引startIndex
        int startIndex = (currentPage - 1) * rows;
        //5.调用dao计算每页数据
        Map map = new HashMap<>();
        map.put("tid", tid);
        map.put("tname", tname);
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
        int totalCount = adminMapper.scoreCount();
        //4.计算总页码
        int totalPage = totalCount % rows == 0 ? totalCount / rows : totalCount / rows + 1;
        //4.1防止越界
        if (totalPage==0) totalPage = 1;
        if (currentPage<1) currentPage = 1;
        if (currentPage > totalPage) currentPage = totalPage;
        //4.2计算开始索引startIndex
        int startIndex = (currentPage - 1) * rows;
        //5.调用dao计算每页数据
        Map map = new HashMap<>();
        map.put("class_name", class_name);
        map.put("startIndex", startIndex);
        map.put("rows", rows);
        map.put("cname", cname);
        map.put("sname", sname);
        List<Score> scores = adminMapper.stuScore(map);
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
        return adminMapper.addStu(stu);
    }

    @Override
    public String queryPswByUserName(String userName) {
        return adminMapper.queryPswByUserName(userName);
    }

    @Override
    public int updateStu(Stu stu) {
        return adminMapper.updateStu(stu);
    }

    @Override
    public int deleteStu(int sid) {
        return adminMapper.deleteScoreById(sid)+adminMapper.deleteStuById(sid)+adminMapper.auto_Stu();
    }

    @Override
    public int addTeacher(Teacher teacher) {
        return adminMapper.addTeacher(teacher);
    }

    @Override
    public int updateTeacher(Teacher teacher) {
        return adminMapper.updateTeacher(teacher);
    }

    @Override
    public int deleteTeacher(int tid) {
        return adminMapper.deleteTeacher(tid) + adminMapper.updateClassTeacher(tid) + adminMapper.updateCourseTeacher(tid)+adminMapper.auto_Teacher();
    }

    @Override
    public String queryPsw(String userName) {
        return adminMapper.queryPsw(userName);
    }

    @Override
    public int addCourse(String cname, int tid) {
        return adminMapper.addCourse(cname, tid);
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
        return adminMapper.updateCourse(course);
    }

    @Override
    public int deleteCourse(int cid) {
        return adminMapper.deleteCourse(cid)+adminMapper.auto_Course();
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
    public int addCLass(String class_name, int tid) {
        return adminMapper.addCLass(class_name, tid);
    }

    @Override
    public int updateClass(Class cl) {
        return adminMapper.updateClass(cl);
    }

    @Override
    public int deleteClass(int class_id) {
        return adminMapper.deleteClass(class_id)+adminMapper.auto_Class();
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
