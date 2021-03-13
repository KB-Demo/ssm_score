package com.sun.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stu {
    private int s_id;
    private String s_name;
    private String s_birth;
    private String s_sex;
    private String s_tel;
    private String s_email;
    private String s_userName;
    private String s_psw;
    private int class_id;
    private String identity;

    private Score score;
    private Course course;
    private Teacher teacher;
    private Class s_class;
    private Profession profession;
    private Faculty faculty;
}
