package com.sun.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 班级表-教师
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Class {
    private int class_id;
    private String class_name;
    private int t_id;

}
