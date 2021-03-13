<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh">
<head>
    <%
        pageContext.setAttribute("APP_PATH", request.getContextPath());
    %>
    <meta charset="utf-8"/>
    <title>注册页面页面</title>
    <link rel="icon" href="${APP_PATH}/static/images/favicon.ico" type="image/ico">
    <link href="${APP_PATH}/static/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="${APP_PATH}/static/css/fonts.css"type="text/css" rel="stylesheet">
    <link href="${APP_PATH}/static/css/style.css"type="text/css" rel="stylesheet">
    <style>
        body{
            /*margin:0px;
            padding:0px;
            font-family:sans-serif;
            background: url(${APP_PATH}/static/images/wallpaper.jpg);
            background-size:cover;*/
            position:fixed;
            top: 0;
            left: 0;
            width:100%;
            height:100%;
            min-width: 1000px;
            z-index:-10;
            zoom: 1;
            background-color: #fff;
            background: url(${APP_PATH}/static/images/wallpaper.jpg);
            background-size: cover;
            -webkit-background-size: cover;
            -o-background-size: cover;
            background-position: center 0;
        }
        .box{
            position:absolute;
            top:50%;
            left:50%;
            transform:translate(-50%,-50%);
            /*实现块元素百分比下居中*/
            width:450px;
            padding:50px;
            background: rgba(0,0,0,.8);
            box-sizing:border-box;
            box-shadow: 0px 15px 25px rgba(0,0,0,.5);
            border-radius:15px;
        }
        .box h2{
            margin:0 0 30px;
            padding:0;
            color: #fff;
            text-align:center;
        }
        .box .inputbox{
            position:relative;
        }
        .box .inputbox input{
            width: 100%;
            padding:10px 0;
            font-size:16px;
            color:#fff;
            letter-spacing: 1px;
            margin-bottom: 30px;
            border:none;
            border-bottom: 1px solid #fff;
            outline:none;
            background: transparent;
        }
        .box .inputbox label{
            position:absolute;
            top:0px;
            left:0px;
            padding:10px 0;
            font-size: 16px;
            color:#fff;
            pointer-events:none;
            transition:.5s;
        }
        .box .inputbox input:focus ~ label,
        .box .inputbox input:valid ~ label
        {
            top:-18px;
            left:0;
            color:#03a9f4;
            font-size:14px;
        }
        .box input[type="submit"]
        {
            background: transparent;
            border:none;
            outline:none;
            font-size: 16px;
            color:#fff;
            background: #03a9f4;
            padding:15px 20px;
            cursor: pointer;
            border-radius:10px;
        }
    </style>
</head>
<body>
<div class="box">
    <h2 style="text-align: center;">用户注册</h2>
    <form action="${pageContext.request.contextPath}/registeredUser" method="post">
        <div class="inputbox">
            <input type="text" name="id" required="" >
            <label>Id</label>
        </div>
        <div class="inputbox">
            <input type="text" name="userName" required="">
            <label>Username</label>
        </div>
        <div class="inputbox">
            <input type="password" name="psw" required="">
            <label>Password</label>
        </div>
        <div class="inputbox">
            <input type="password" name="checkPsw" required="">
            <label>Check</label>
        </div>
        <div class="example-box">
            <label class="ftdms-radio radio-inline radio-primary">
                <input type="radio" value="stu" name="register" checked="checked"><span>学生</span>
            </label>
            <label class="ftdms-radio radio-inline radio-primary">
                <input type="radio" value="teacher" name="register"><span>教师</span>
            </label>
        </div>
        <hr>
        <input type="submit"  value="注册">
        <a href="${pageContext.request.contextPath}/toLogin"><input class="btn-link" type="button" value="返回"></a>
        <strong  style="color: white;font-weight: bold">${regist_msg}</strong>
    </form>
</div>
</body>
</html>