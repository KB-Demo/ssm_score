package com.sun.service;

import com.sun.dao.ScoreMapper;
import com.sun.pojo.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    ScoreMapper scoreMapper;

    @Override
    public List<Score> queryAllScore() {
        return scoreMapper.queryAllScore();
    }

    @Override
    public List<Score> queryScoreBySid(int s_id) {
        return scoreMapper.queryScoreBySid(s_id);
    }

    @Override
    public List<Score> queryScoreByCid(int c_id) {
        return scoreMapper.queryScoreBySid(c_id);
    }

    @Override
    public List<Score> queryAllScoreAndStu() {
        return scoreMapper.queryAllScoreAndStu();
    }
}
