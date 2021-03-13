package com.sun.pojo;

public class Education {
    private static String e1 = "本科";
    private static String e2 = "硕士";
    private static String e3 = "博士";
    private volatile static String[] Educations;

    public static String[] getEducations(){
        if (Educations == null) {
            synchronized (Education.class){
                if (Educations == null) {
                    Educations = new String[]{e1, e2, e3};
                }
            }
        }
        return Educations;
    }



}
