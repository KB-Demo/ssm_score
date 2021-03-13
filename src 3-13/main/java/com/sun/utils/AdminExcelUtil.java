package com.sun.utils;

import com.sun.pojo.*;
import com.sun.pojo.Class;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class AdminExcelUtil {
    public void InfoExcelByList(HttpServletResponse response, List list,String excel_msg) throws IOException {
        response.setCharacterEncoding("utf-8");
        //创建excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        //课程表
        if ("courses".equalsIgnoreCase(excel_msg)) {
            //创建sheet页
            HSSFSheet sheet = wb.createSheet("课程信息表");
            //创建标题行
            HSSFRow titleRow = sheet.createRow(0);
            titleRow.createCell(0).setCellValue("课程号");
            titleRow.createCell(1).setCellValue("课程名");
            titleRow.createCell(2).setCellValue("授课老师");
            for (Object o : list) {
                Course course = (Course) o;
                HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum()+1);
                dataRow.createCell(0).setCellValue(course.getC_id());
                dataRow.createCell(1).setCellValue(course.getC_name());
                dataRow.createCell(2).setCellValue(course.getTeacher().getT_name());
            }
            response.setHeader("Content-Disposition", "attachment;filename="
                    + new String("课程信息表".getBytes(), "iso-8859-1") + ".xls");
        }
        //班级表
        if ("classes".equalsIgnoreCase(excel_msg)) {
            //创建sheet页
            HSSFSheet sheet = wb.createSheet("班级信息表");
            //创建标题行
            HSSFRow titleRow = sheet.createRow(0);
            titleRow.createCell(0).setCellValue("班级号");
            titleRow.createCell(1).setCellValue("班级名");
            titleRow.createCell(2).setCellValue("班主任");
            for (Object o : list) {
                Class cl = (Class) o;
                HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum()+1);
                dataRow.createCell(0).setCellValue(cl.getClass_id());
                dataRow.createCell(1).setCellValue(cl.getClass_name());
                dataRow.createCell(2).setCellValue(cl.getTeacher().getT_name());
            }
            response.setHeader("Content-Disposition", "attachment;filename="
                    + new String("班级信息表".getBytes(), "iso-8859-1") + ".xls");
        }
        //平均分表
        if ("classAvgScore".equalsIgnoreCase(excel_msg)) {
            //创建sheet页
            HSSFSheet sheet = wb.createSheet("班级平均分表");
            //创建标题行
            HSSFRow titleRow = sheet.createRow(0);
            titleRow.createCell(0).setCellValue("班级");
            titleRow.createCell(1).setCellValue("班主任");
            titleRow.createCell(2).setCellValue("平均分");
            titleRow.createCell(3).setCellValue("排名");
            for (Object o : list) {
                Class cl = (Class) o;
                HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum()+1);
                dataRow.createCell(0).setCellValue(cl.getClass_name());
                dataRow.createCell(1).setCellValue(cl.getTeacher().getT_name());
                dataRow.createCell(2).setCellValue(cl.getScore().getS_score());
                dataRow.createCell(3).setCellValue(cl.getRank());
            }
            response.setHeader("Content-Disposition", "attachment;filename="
                    + new String("班级平均分表".getBytes(), "iso-8859-1") + ".xls");
        }
        response.setContentType("application/octet-stream;charset=utf-8");
        OutputStream os = response.getOutputStream();
        wb.write(os);
        os.flush();
        os.close();
    }


    public void InfoExcelByPageBean(HttpServletResponse response, PageBean pb) throws IOException {


        response.setCharacterEncoding("utf-8");
        List list = pb.getList();
        //创建excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        //判断表
        //学生信息
        if ("stuPerson".equalsIgnoreCase(pb.getExcel())) {
            //创建sheet页
            HSSFSheet sheet = wb.createSheet("学生信息表");
            //创建标题行
            HSSFRow titleRow = sheet.createRow(0);
            titleRow.createCell(0).setCellValue("学号");
            titleRow.createCell(1).setCellValue("姓名");
            titleRow.createCell(2).setCellValue("出生日期");
            titleRow.createCell(3).setCellValue("性别");
            titleRow.createCell(4).setCellValue("电话号码");
            titleRow.createCell(5).setCellValue("邮箱");
            titleRow.createCell(6).setCellValue("班级");
            titleRow.createCell(7).setCellValue("专业");
            titleRow.createCell(8).setCellValue("院系");
            titleRow.createCell(9).setCellValue("账号");
            titleRow.createCell(10).setCellValue("密码");
            for (Object o : list) {
                Stu stu = (Stu) o;
                HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                dataRow.createCell(0).setCellValue(stu.getS_id());
                dataRow.createCell(1).setCellValue(stu.getS_name());
                dataRow.createCell(2).setCellValue(stu.getS_birth());
                dataRow.createCell(3).setCellValue(stu.getS_sex());
                dataRow.createCell(4).setCellValue(stu.getS_tel());
                dataRow.createCell(5).setCellValue(stu.getS_email());
                dataRow.createCell(6).setCellValue(stu.getS_class().getClass_name());
                dataRow.createCell(7).setCellValue(stu.getProfession().getP_name());
                dataRow.createCell(8).setCellValue(stu.getFaculty().getF_name());
                dataRow.createCell(9).setCellValue(stu.getS_userName());
                dataRow.createCell(10).setCellValue(stu.getS_psw());
            }
            response.setHeader("Content-Disposition", "attachment;filename="
                    + new String("学生信息表".getBytes(), "iso-8859-1") + ".xls");
        }
        //教师信息表
        if ("teacherPerson".equalsIgnoreCase(pb.getExcel())) {
            //创建sheet页
            HSSFSheet sheet = wb.createSheet("教师信息表");
            //创建标题行
            HSSFRow titleRow = sheet.createRow(0);
            titleRow.createCell(0).setCellValue("教师号");
            titleRow.createCell(1).setCellValue("姓名");
            titleRow.createCell(2).setCellValue("出生日期");
            titleRow.createCell(3).setCellValue("性别");
            titleRow.createCell(4).setCellValue("电话号码");
            titleRow.createCell(5).setCellValue("邮箱");
            titleRow.createCell(6).setCellValue("带领班级");
            titleRow.createCell(7).setCellValue("职位");
            titleRow.createCell(8).setCellValue("学历");
            titleRow.createCell(9).setCellValue("账号");
            titleRow.createCell(10).setCellValue("密码");
            for (Object o : list) {
                Teacher teacher = (Teacher) o;
                HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum()+1);
                dataRow.createCell(0).setCellValue(teacher.getT_id());
                dataRow.createCell(1).setCellValue(teacher.getT_name());
                dataRow.createCell(2).setCellValue(teacher.getT_birth());
                dataRow.createCell(3).setCellValue(teacher.getT_sex());
                dataRow.createCell(4).setCellValue(teacher.getT_tel());
                dataRow.createCell(5).setCellValue(teacher.getT_email());
                dataRow.createCell(6).setCellValue(teacher.getT_class().getClass_name());
                dataRow.createCell(7).setCellValue(teacher.getT_position());
                dataRow.createCell(8).setCellValue(teacher.getT_education());
                dataRow.createCell(9).setCellValue(teacher.getT_userName());
                dataRow.createCell(10).setCellValue(teacher.getT_psw());
            }
            response.setHeader("Content-Disposition", "attachment;filename="
                    + new String("教师信息表".getBytes(), "iso-8859-1") + ".xls");
        }

        //成绩表
        if ("stuScore".equalsIgnoreCase(pb.getExcel())) {
            //创建sheet页
            HSSFSheet sheet = wb.createSheet("学生成绩表");
            //创建标题行
            HSSFRow titleRow = sheet.createRow(0);
            titleRow.createCell(0).setCellValue("学号");
            titleRow.createCell(1).setCellValue("姓名");
            titleRow.createCell(2).setCellValue("课程名");
            titleRow.createCell(3).setCellValue("分数");
            titleRow.createCell(4).setCellValue("排行");
            titleRow.createCell(5).setCellValue("班级");
            for (Object o : list) {
                Score score = (Score) o;
                HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum()+1);
                dataRow.createCell(0).setCellValue(score.getS_id());
                dataRow.createCell(1).setCellValue(score.getStu().getS_name());
                dataRow.createCell(2).setCellValue(score.getCourse().getC_name());
                dataRow.createCell(3).setCellValue(score.getS_score());
                dataRow.createCell(4).setCellValue(score.getRank());
                dataRow.createCell(5).setCellValue(score.getSc_class().getClass_name());
            }
            response.setHeader("Content-Disposition", "attachment;filename="
                    + new String("学生成绩表".getBytes(), "iso-8859-1") + ".xls");
        }
        //平均分表
        if ("classAvgScore".equalsIgnoreCase(pb.getExcel())) {
            //创建sheet页
            HSSFSheet sheet = wb.createSheet("班级平均分表");
            //创建标题行
            HSSFRow titleRow = sheet.createRow(0);
            titleRow.createCell(0).setCellValue("班级");
            titleRow.createCell(1).setCellValue("班主任");
            titleRow.createCell(2).setCellValue("平均分");
            titleRow.createCell(3).setCellValue("排名");
            for (Object o : list) {
                Class cl = (Class) o;
                HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum()+1);
                dataRow.createCell(0).setCellValue(cl.getClass_name());
                dataRow.createCell(1).setCellValue(cl.getTeacher().getT_name());
                dataRow.createCell(2).setCellValue(cl.getScore().getS_score());
                dataRow.createCell(3).setCellValue(cl.getRank());
            }
            response.setHeader("Content-Disposition", "attachment;filename="
                    + new String("班级平均分表".getBytes(), "iso-8859-1") + ".xls");
        }
        response.setContentType("application/octet-stream;charset=utf-8");
        OutputStream os = response.getOutputStream();
        wb.write(os);
        os.flush();
        os.close();
    }
}
