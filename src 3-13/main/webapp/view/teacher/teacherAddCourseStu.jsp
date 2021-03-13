<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh">
<head>
    <%
        pageContext.setAttribute("APP_PATH", request.getContextPath());
    %>
    <meta charset="utf-8">
    <title>学生成绩系统-总成绩排行</title>
    <link rel="icon" href="${APP_PATH}/static/images/favicon.ico" type="image/ico">
    <link href="${APP_PATH}/static/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="${APP_PATH}/static/css/fonts.css"type="text/css" rel="stylesheet">
    <link href="${APP_PATH}/static/css/style.css"type="text/css" rel="stylesheet">
    <script>
        function fun() {
            //使用$.ajax()发送异步请求
            $.ajax({
                /*async:false,*/
                url:"${pageContext.request.contextPath}/teacher/checkInsertScore",//请求路径
                type:"POST",
                dateType:"json",
                data:{sid:$("#sid").val(),cid:$("#cid option:selected").val(),score:$("#score").val()},
                success: function (data) {
                    alert("进入success");
                        console.log(data);
                        if (data.msg){
                            alert("添加成功！");
                            window.location.href="${pageContext.request.contextPath}/teacher/courseStuScore";
                        }else{
                            alert("信息错误或该学生已经存在成11绩！");
                            window.location.href="${pageContext.request.contextPath}/teacher/toAddStuScore"
                        }
                },
                error:function () {
                    alert("信息错误或该学生已经存在成绩!");
                    //window.location.href="<%--${pageContext.request.contextPath}--%>/teacher/toAddStuScore"
                }

            });
        }

    </script>
</head>
<body>
<div class="ftdms-layout-web">
    <div class="ftdms-layout-container">
        <!--左侧导航-->
        <aside class="ftdms-layout-sidebar">
            <!-- logo -->
            <div id="logo" class="sidebar-header">
                <a href="https://www.gcu.edu.cn/"><img src="${APP_PATH}/static/images/logo.png" /></a>
            </div>
            <div class="ftdms-layout-sidebar-scroll">
                <nav class="sidebar-main">
                    <ul class="nav nav-drawer">
                        <li class="nav-item active"> <a href="${pageContext.request.contextPath}/teacher/index"><i class="ftsucai-82"></i>个人课程</a> </li>
                        <li class="nav-item  nav-item-has-subnav">
                            <a href="javascript:void(0)"><i class="ftsucai-edit-2"></i>个人信息编辑</a>
                            <ul class="nav nav-subnav">
                                <li> <a href="${pageContext.request.contextPath}/teacher/personal">个人信息</a> </li>
                                <li> <a href="${pageContext.request.contextPath}/teacher/toUpdate">修改</a> </li>
                            </ul>
                        <li class="nav-item nav-item-has-subnav">
                            <a href="javascript:void(0)"><i class="ftsucai-edit-2"></i>学生列表</a>
                            <ul class="nav nav-subnav">
                                <li> <a href="${pageContext.request.contextPath}/teacher/myCourseStu">课程学生</a> </li>
                                <li> <a href="${pageContext.request.contextPath}/teacher/myClassStu">班级学生</a> </li>
                            </ul>
                        <li class="nav-item nav-item-has-subnav active open ">
                            <a href="javascript:void(0)"><i class="ftsucai-edit-2"></i>成绩管理</a>
                            <ul class="nav nav-subnav">
                                <li class="active"> <a href="${pageContext.request.contextPath}/teacher/courseStuScore">课程学生成绩</a> </li>
                                <li> <a href="${pageContext.request.contextPath}/teacher/classStuScore">班级学生成绩</a> </li>
                                <li> <a href="${pageContext.request.contextPath}/teacher/classStuAvgScore">班级学生平均成绩</a> </li>
                                <li> <a href="${pageContext.request.contextPath}/teacher/classStuSumScore">班级学生总成绩</a> </li>
                            </ul>
                    </ul>
                </nav>
            </div>
        </aside>
        <!--End 左侧导航-->

        <!--头部信息-->
        <header class="ftdms-layout-header">

            <nav class="navbar navbar-default">
                <div class="topbar">

                    <div class="topbar-left">
                        <div class="ftdms-aside-toggler">
                            <span class="ftdms-toggler-bar"></span>
                            <span class="ftdms-toggler-bar"></span>
                            <span class="ftdms-toggler-bar"></span>
                        </div>
                        <span class="navbar-page-title"> 成绩管理-课程学生成绩-添加学生 </span>
                    </div>

                    <ul class="topbar-right">
                        <li class="dropdown dropdown-profile">
                            <a href="javascript:void(0)" data-toggle="dropdown">
                                <span>${name_msg}教师，你好！<span class="caret"></span></span>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-right">
                                <li> <a href="${pageContext.request.contextPath}/teacher/toChangePsw"><i class="ftsucai-edit-2"></i> 修改密码</a> </li>
                                <li class="divider"></li>
                                <li><a href="${pageContext.request.contextPath}/teacher/exit"><i class="ftsucai-exit2"></i> 退出登录</a> </li>
                            </ul>
                        </li>
                    </ul>

                </div>
            </nav>

        </header>
        <!--End 头部信息-->

        <!--页面主要内容-->
        <main class="ftdms-layout-content">
            <div class="container-fluid">

                <div class="row">
                    <div class="col-lg-12">
                        <div class="card">
                            <%--显示个人成绩--%>
                            <div class="card-body">

                                <div class="table-responsive">

                                    <%--
                                     出现的问题：提交sql请求，修改失败，初次考虑，是事务问题，使用aop横切配置事务后依然失败
                                     修改id需要使用隐藏域hidden把id提交
                                    --%>

                                    <div class="card">
                                        <div class="card-header"><h4>添加学生</h4></div>
                                        <div class="card-body">

                                            <form action="${pageContext.request.contextPath}/teacher/addStuScore" method="post">
                                                <span style="color: red;font-weight:bold">${error_msg}</span>
                                                <div class="form-group">
                                                    <label for="sid">学号</label>
                                                    <input class="form-control" name="sid" type="text" id="sid" placeholder="请输入学生学号..">
                                                </div>
                                                <div class="form-group">
                                                    <label for="cid">课程选择</label>

                                                        <select class="form-control" <%--style="width: 600px"--%> id="cid" name="cid">
                                                            <option  value="notSelet">请选择</option>
                                                       <c:forEach var="course" items="${requestScope.get('courses')}" >
                                                            <option  name="cid" value="${course.course.c_id}">${course.course.c_name}</option>
                                                       </c:forEach>
                                                   </select>

                                                </div>
                                                <div class="form-group">
                                                    <label for="score">科目成绩</label>
                                                    <input class="form-control" name="score" type="text" id="score" placeholder="请输入课程成绩..">
                                                </div>

                                                <div class="example-box" style="text-align: center">
                                                    <input class="btn btn-success btn-w-md" type="submit" value="提交" />
                                                    <input class="btn btn-secondary" type="reset" value="重置" />
                                                    <a href="${pageContext.request.contextPath}/teacher/courseStuScore"><input class="btn btn-warning" type="button" value="返回"></a>
                                                </div>

                                            </form>

                                        </div>
                                    </div>

                                </div>
                            </div>

                        </div>
                    </div>
                </div>

            </div>
        </main>
        <!--页面主要内容-->
    </div>
</div>
<script type="text/javascript" src="${APP_PATH}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/js/main.min.js"></script>
</body>
</html>
