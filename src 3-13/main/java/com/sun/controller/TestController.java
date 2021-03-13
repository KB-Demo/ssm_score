package com.sun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class TestController {

    @RequestMapping("/toTest")
    public String toTest(){
        return "test/ajaxTest";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public List list(){
        ArrayList list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        return list;
    }
}
