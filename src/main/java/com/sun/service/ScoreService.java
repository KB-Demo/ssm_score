package com.sun.service;

import com.sun.pojo.Score;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScoreService {
    //查询所有成绩
    List<Score> queryAllScore();

    //通过学生s_id查成绩
    List<Score> queryScoreBySid(@Param("s_id") int s_id);

    //通过查询课程成绩
    List<Score> queryScoreByCid(@Param("c_id") int c_id);

    //查询所有成绩对应学生信息
    List<Score> queryAllScoreAndStu();
}
