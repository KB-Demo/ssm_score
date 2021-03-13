package com.sun.pojo;

public class Position {
    private static String p1 = "助教";
    private static String p2 = "讲师";
    private static String p3 = "工程师";
    private static String p4 = "高级工程师";
    private static String p5 = "教授";

    private volatile static String[] POSITIONS;

    public static String[] getPositions(){
        if (POSITIONS == null) {
            synchronized (Position.class){
                if (POSITIONS == null) {
                    POSITIONS = new String[]{p1, p2, p3, p4, p5};
                }
            }
        }
        return POSITIONS;
    }
}
