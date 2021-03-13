package com.sun.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Score {
    private int s_id;
    private int c_id;
    private int s_score;
    private int rank;
    private Stu stu;
    private Course course;
    private int count;
    private Teacher teacher;
    private Class sc_class;

}
