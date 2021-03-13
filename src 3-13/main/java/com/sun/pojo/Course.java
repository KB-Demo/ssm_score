package com.sun.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    private int c_id;
    private String c_name;
    private int t_id;
    //存储学分
    private double credit;

    private Teacher teacher;
    private Stu stu;
   /* private Course course;*/

}
