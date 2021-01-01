<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh">
<head>
    <meta charset="utf-8"/>
    <title>登录页面</title>
    <link rel="icon" href="${APP_PATH}/static/images/favicon.ico" type="image/ico">
    <link href="${APP_PATH}/static/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="${APP_PATH}/static/css/fonts.css"type="text/css" rel="stylesheet">
    <link href="${APP_PATH}/static/css/style.css"type="text/css" rel="stylesheet">
    <style>
        #div01{
            background:#FFF;
            width:500px;
            height:500px;
            opacity:0.5;
        }
        .loginpage {
            position: relative;
        }
        .login {
            display: flex !important;
            min-height: 100vh;
            align-items: center !important;
            justify-content: center !important;
        }
        .login-center {
            background: #fff;
            min-width: 38rem;
            padding: 2em 3em;
            border-radius: 10px;
            margin: 2em 0;
        }
        .login-header {
            margin-bottom: 2rem !important;
        }
        .login-center .has-feedback.feedback-left .form-control {
            padding-left: 38px;
            padding-right: 12px;
        }
        .login-center .has-feedback.feedback-left .form-control-feedback {
            left: 0;
            right: auto;
            width: 38px;
            height: 38px;
            line-height: 38px;
            z-index: 4;
            color: #dcdcdc;
        }
        .login-center .has-feedback.feedback-left.row .form-control-feedback {
            left: 15px;
        }
    </style>
</head>
<body <%--style="background:url(${APP_PATH}/static/images/wallpaper.jpg);background-size: 100% 100%"--%>>
<div class="loginpage">
    <div class="login">
        <div class="login-center">
            <div class="login-header text-center">
                <h3 style="text-align: center;">成绩管理系统</h3>
            </div>
            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="form-group has-feedback feedback-left">
                    <input type="text" placeholder="请输入您的用户名" class="form-control" name="userName" id="username" />
                    <span class="ftsucai-65 form-control-feedback" aria-hidden="true"></span>
                </div>
                <div class="form-group has-feedback feedback-left">
                    <input type="password" placeholder="请输入密码" class="form-control" id="password" name="psw" />
                    <span class="ftsucai-216 form-control-feedback" aria-hidden="true"></span>
                </div>
                <%--<p class="m-t-10">登录用户选择:</p>--%>
                <div class="example-box">
                    <label class="ftdms-radio radio-inline radio-primary">
                        <input type="radio" value="stu" name="logger" checked="checked"><span>学生</span>
                    </label>
                    <label class="ftdms-radio radio-inline radio-primary">
                        <input type="radio" value="admin" name="logger"><span>教师</span>
                    </label>
                    <label class="ftdms-radio radio-inline radio-primary">
                        <input type="radio" value="admin" name="logger"><span>管理员</span>
                    </label>
                </div>
                <hr/>
                <div class="example-box">
                    <input class="btn btn-w-xl btn-primary" type="submit" value="登录"/>
                    <input class="btn btn-w-xs btn-warning" type="reset" value="重置" />
                    <a href="${pageContext.request.contextPath}/toRegistered"><input class="btn-link" type="button" value="注册"></a>
                    <%--<input class="btn btn-w-sm btn-yellow" type="submit" value="注册">--%>
                    <%--<button class="btn btn-block btn-primary" ><a href="${pageContext.request.contextPath}/stu/toChangePsw">注册</a></button>--%>
                </div>
            </form>
        <div>
            <strong  style="color: black;font-weight: bold">${login_msg}</strong>
        </div>
    </div>
    </div>
</div>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript">;</script>
</body>
</html>