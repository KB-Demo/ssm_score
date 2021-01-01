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
    private String t_tel;
    private String t_email;
    private String t_userName;
    private String t_psw;
}
