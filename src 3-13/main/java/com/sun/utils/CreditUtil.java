package com.sun.utils;

import java.math.BigDecimal;

public class CreditUtil {
    public static double pointOfEachCourse(double credit,int grade){//根据实际成绩判断学分方法
        double point = 0.0;
        if(grade>=90&&grade<=100)
            point=credit;

        if(grade>=85&&grade<=89)
            point=0.925*credit;

        if(grade>=82&&grade<=84)
            point=0.825*credit;

        if(grade>=78&&grade<=81)
            point=0.75*credit;

        if(grade>=75&&grade<=77)
            point=0.675*credit;

        if(grade>=72&&grade<=74)
            point=0.575*credit;

        if(grade>=68&&grade<=71)
            point=0.5*credit;

        if(grade>=64&&grade<=67)
            point=0.375*credit;

        if(grade>=60&&grade<=63)
            point=0.25*credit;

        if(grade<60)
            point=0.0;
        double result = new BigDecimal(point).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }
}
