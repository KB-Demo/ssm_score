package com.sun.service;

import com.sun.dao.StuMapper;
import com.sun.utils.CreditUtil;
import com.sun.utils.PageBean;
import com.sun.pojo.Stu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StuServiceImpl implements StuService {

    @Autowired
    StuMapper stuMapper;

    @Override
    public int addStu(Stu stu) {
        return stuMapper.addStu(stu);
    }

    @Override
    public int deleteStu(int id) {
        return stuMapper.deleteStu(id);
    }

    @Override
    public int updateStu(Stu stu) {
        return stuMapper.updateStu(stu);
    }

    @Override
    public Stu queryStuById(int id) {
        return stuMapper.queryStuById(id);
    }

    @Override
    public List<Stu> queryAllStu() {
        return stuMapper.queryAllStu();
    }

    @Override
    public String stuLoginCheck(String userName) {
        return stuMapper.stuLoginCheck(userName);
    }

    @Override
    public Stu queryStuByUserName(String userName) {
        return stuMapper.queryStuByUserName(userName);
    }

    @Override
    public List<Stu> queryStuScoreCourse(String userName, String c_name) {
        List<Stu> stus = stuMapper.queryStuScoreCourse(userName, c_name);
        List<Stu> all_rank = stuMapper.queryScoreByUserName(stuMapper.queryStuByUserName(userName).getS_class().getClass_id());
        //存储当前用户信息
        List<Stu> list = new ArrayList();
        for (Stu stu : all_rank) {
            if (userName.equals(stu.getS_userName())) {
                /*System.out.println(stu+":all_rank");*/
                list.add(stu);
            }
        }
        /*System.out.println(list+":list");*/
        for (int i = 0; i < stus.size(); i++) {
            Stu stu = stus.get(i);
            System.out.println(stu);
            stu.getCourse().setCredit(CreditUtil.pointOfEachCourse(stu.getCourse().getCredit(), stu.getScore().getS_score()));
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getCourse().getC_name().equals(stu.getCourse().getC_name())) {
                    stu.getScore().setRank(list.get(j).getScore().getRank());
                }
            }
        }
        return stus;
    }

    @Override
    public PageBean<Stu> findCourseRankByPage(String _currentPage, String _rows, String c_name, Stu stu) {
        //1.创建空的PageBean对象
        PageBean<Stu> pb = new PageBean<>();
        //2.设置参数 currentPage当前页 rows每页数据量
        int currentPage = Integer.parseInt(_currentPage);
        int rows = Integer.parseInt(_rows);
        //3.调用dao查询记录数by科目成绩 totalCount总记录数
        int totalCount = stuMapper.queryCountStuByCourse(c_name,stu.getS_class().getClass_id());
        //4.计算总页码 totalPage
        int totalPage = totalCount % rows == 0 ? totalCount / rows : totalCount / rows + 1;
        //4.1防止越界
        if (totalPage == 0) {
            totalPage = 1;//没查出的时候totalPage==0，会让currentPage越界}
            pb.setError("未查到该课程");
        }
        if (currentPage <= 0) currentPage = 1;
        if (currentPage > totalPage) currentPage = totalPage;
        //System.out.println("currentPage:"+currentPage+"--totalPage:"+totalPage);
        //4.2计算开始索引 = (当前页-1)*每页数据
        int startIndex = (currentPage - 1) * rows;
        //5.调用dao查询当前页数据
        Map map = new HashMap();
        map.put("startIndex", startIndex);
        map.put("rows", rows);
        map.put("cname", c_name);
        map.put("class_id", stu.getS_class().getClass_id());
        System.out.println("rows="+rows+",startIndex="+startIndex+",cname="+c_name+",class_id="+ stu.getS_class().getClass_id()+",totalPage"+totalPage+":"+totalCount);
        System.out.println(stu.getS_class().getClass_id());
        List<Stu> stus = stuMapper.queryScoreRankByCourse(map);
        //6.封装数据到pb对象
        pb.setTotalPage(totalPage);
        pb.setTotalCount(totalCount);
        pb.setRows(rows);
        pb.setCurrentPage(currentPage);
        pb.setList(stus);
        return pb;
    }


    @Override
    public PageBean<Stu> findRankByPage(String _currentPage, String _rows,Stu stu) {
        //1.创建空的PageBean对象
        PageBean<Stu> pb = new PageBean<>();
        //2.设置参数
        int currentPage = Integer.parseInt(_currentPage);
        int rows = Integer.parseInt(_rows);

        //3.调用dao查询总记录数
        int totalCount = stuMapper.queryCountStu(stu.getS_class().getClass_id());
        //4.计算总页码
        int totalPage = totalCount % rows == 0 ? totalCount / rows : totalCount / rows + 1;
        //4.1防止越界
        if (currentPage <= 0) {
            currentPage = 1;
        }
        if (currentPage > totalPage) {
            currentPage = totalPage;
        }
        //4.2计算开始的记录索引
        int startIndex = (currentPage - 1) * rows;

        //5.调用dao查询List集合
        Map<String,Integer> map = new HashMap<>();
        map.put("class_id", stu.getS_class().getClass_id());
        System.out.println(stu.getS_class().getClass_id());
        map.put("startIndex", startIndex);
        map.put("rows", rows);
        List<Stu> stus = stuMapper.queryScoreRank(map);

        pb.setTotalCount(totalCount);
        pb.setTotalPage(totalPage);
        pb.setList(stus);
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);
        return pb;
    }

    @Override
    public int updateStuPsw(String userName, String psw) {
        return stuMapper.updateStuPsw(userName,psw);
    }

    @Override
    public int stuRegistered(int id, String userName, String psw) {
        return stuMapper.stuRegistered(id, userName, psw);
    }

    @Override
    public List<Stu> queryScoreByUserName(int class_id) {
        return stuMapper.queryScoreByUserName(class_id);
    }


}
