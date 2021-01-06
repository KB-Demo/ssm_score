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
    private Stu stu;
    private Course course;
    private int rank;
    private int count;
}
