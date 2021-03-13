package com.sun.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    private int t_id;
    private String t_name;
    private String t_birth;
    private String t_sex;
    private String t_tel;
    private String t_email;
    private String t_userName;
    private String t_psw;
    private String identity;
    private String t_position;//职位
    private String t_education;//学历


    private Stu stu;
    private Course course;
    private Class t_class;
    private Score score;
    private Profession profession;
    private Faculty faculty;
}
