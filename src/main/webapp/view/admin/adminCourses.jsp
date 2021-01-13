<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <title>学生成绩系统-课程信息</title>
    <link rel="icon" href="${APP_PATH}/static/images/favicon.ico" type="image/ico">
    <link href="${APP_PATH}/static/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="${APP_PATH}/static/css/fonts.css"type="text/css" rel="stylesheet">
    <link href="${APP_PATH}/static/css/style.css"type="text/css" rel="stylesheet">
    <script>
        function deleteCourse(c_id){
            //用户安全提示
            if (confirm("你确定要删除该课程吗？")) {
                location.href="${pageContext.request.contextPath}/admin/deleteCourse?c_id="+c_id;
            }
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
                        <li class="nav-item "> <a href="${pageContext.request.contextPath}/admin/stuPerson"><i class="ftsucai-82"></i>学生信息</a> </li>
                        <li class="nav-item "> <a href="${pageContext.request.contextPath}/admin/teacherPerson"><i class="ftsucai-82"></i>教师信息</a> </li>
                        <li class="nav-item active"> <a href="${pageContext.request.contextPath}/admin/courses"><i class="ftsucai-82"></i>课程信息</a> </li>
                        <li class="nav-item "> <a href="${pageContext.request.contextPath}/admin/classes"><i class="ftsucai-82"></i>班级信息</a> </li>
                        <li class="nav-item nav-item-has-subnav">
                            <a href="javascript:void(0)"><i class="ftsucai-edit-2"></i>成绩报表</a>
                            <ul class="nav nav-subnav">
                                <li> <a href="${pageContext.request.contextPath}/admin/stuScore">学生成绩</a> </li>
                                <li> <a href="${pageContext.request.contextPath}/admin/classAvgScore">班级平均分</a> </li>
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
                        <span class="navbar-page-title"> 课程信息 </span>
                    </div>

                    <ul class="topbar-right">
                        <li class="dropdown dropdown-profile">
                            <a href="javascript:void(0)" data-toggle="dropdown">
                                <span>管理员，你好！<span class="caret"></span></span>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-right">
                                <li> <a href="${pageContext.request.contextPath}/admin/toChangePsw"><i class="ftsucai-edit-2"></i> 修改密码</a> </li>
                                <li class="divider"></li>
                                <li><a href="${pageContext.request.contextPath}/admin/exit"><i class="ftsucai-exit2"></i> 退出登录</a> </li>
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
                            <div class="card-toolbar clearfix">
                                <%--添加课程--%>
                                <a class="btn btn-primary m-r-5" href="${pageContext.request.contextPath}/admin/toAddCourse"> 添加课程</a>
                                <%--导出excel--%>
                                <a class="btn btn-success m-r-5" href="${pageContext.request.contextPath}/"> 导出Excel</a>
                                <%--按课程名称查询--%>
                                <form class="form-inline pull-right" action="${pageContext.request.contextPath}/admin/courses" method="post" >
                                    <span style="color: red;font-weight:bold">${error}</span>
                                    <div class="form-group">
                                        <input class="form-control" type="text" name="cname" placeholder="请输入课程名..">
                                    </div>
                                    <div class="form-group">
                                        <input class="form-control" type="text"  name="tname" placeholder="请输入教师姓名..">
                                    </div>
                                    <div class="form-group">
                                        <button class="btn btn-success" type="submit">查询</button>
                                    </div>
                                </form>
                            </div>

                            <%--显示个人成绩--%>
                            <div class="card-body">

                                <div class="table-responsive">
                                    <table class="table table-bordered">
                                        <thead>
                                        <tr>
                                            <th>课程号</th>
                                            <th>课程名</th>
                                            <th>授课老师</th>
                                            <th>操作</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="course" items="${requestScope.get('courses')}" >
                                            <tr>
                                                <td>${course.c_id}</td>
                                                <td>${course.c_name}</td>
                                                <td>${course.teacher.t_name}</td>
                                                <td> <a class="btn btn-xs btn-default" href="${pageContext.request.contextPath}/admin/toUpdateCourse?c_id=${course.c_id}&c_name=${course.c_name}&t_name=${course.teacher.t_name}&t_id=${course.teacher.t_id}" title="编辑" data-toggle="tooltip"><i class="ftsucai-edit-2"></i></a>
                                                    <a class="btn btn-xs btn-default" href="javascript:deleteCourse(${course.c_id});" title="删除" data-toggle="tooltip"><i class="ftsucai-del-2"></i></a></td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>

                                    </table>
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
<script type="text/javascript" src="${APP_PATH}/static//js/baidu.js"></script>
</body>
</html>

