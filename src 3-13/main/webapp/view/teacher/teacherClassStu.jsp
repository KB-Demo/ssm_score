<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh">
<head>
    <%
        pageContext.setAttribute("APP_PATH", request.getContextPath());
    %>
    <meta charset="utf-8">
    <title>学生成绩系统-学生端</title>
    <link rel="icon" href="${APP_PATH}/static/images/favicon.ico" type="image/ico">
    <link href="${APP_PATH}/static/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="${APP_PATH}/static/css/fonts.css"type="text/css" rel="stylesheet">
    <link href="${APP_PATH}/static/css/style.css"type="text/css" rel="stylesheet">
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
                        <li class="nav-item nav-item-has-subnav active open">
                            <a href="javascript:void(0)"><i class="ftsucai-edit-2"></i>学生列表</a>
                            <ul class="nav nav-subnav">
                                <li> <a href="${pageContext.request.contextPath}/teacher/myCourseStu">课程学生</a> </li>
                                <li class="active"> <a href="${pageContext.request.contextPath}/teacher/myClassStu">班级学生</a> </li>
                            </ul>
                        <li class="nav-item nav-item-has-subnav">
                            <a href="javascript:void(0)"><i class="ftsucai-edit-2"></i>成绩管理</a>
                            <ul class="nav nav-subnav">
                                <li> <a href="${pageContext.request.contextPath}/teacher/courseStuScore">课程学生成绩</a> </li>
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
                        <span class="navbar-page-title"> 学生列表-班级学生 </span>
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
                            <div class="card-toolbar clearfix">
                                <%--导出excel--%>
                                <a class="btn btn-success m-r-5" href="${pageContext.request.contextPath}/teacher/classStuInfo?totalCount=${pb.totalCount}&sid=${sid}&sname=${sname}"> 导出Excel</a>
                                <%--按课程名称查询--%>
                                <form class="form-inline pull-right" action="${pageContext.request.contextPath}/teacher/myClassStu" method="post" >
                                    <span style="color: red;font-weight:bold">${pb.error}</span>
                                    <div class="form-group">
                                        <%--<label class="sr-only" for="example-if-email">邮箱</label>--%>
                                        <input class="form-control" type="text" name="sid" placeholder="请输入学号..">
                                    </div>
                                    <div class="form-group">
                                        <%--<label class="sr-only" for="example-if-password">密码</label>--%>
                                        <input class="form-control" type="text"  name="sname" placeholder="请输入姓名..">
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
                                                <th>学号</th>
                                                <th>姓名</th>
                                                <th>出生日期</th>
                                                <th>性别</th>
                                                <th>电话</th>
                                                <th>邮箱</th>
                                                <%--<th>班级</th>--%>
                                            </tr>
                                            </thead>

                                            <tbody>
                                            <c:forEach var="teacher" items="${requestScope.get('teachers')}" >
                                                <tr>
                                                    <td>${teacher.stu.s_id}</td>
                                                    <td>${teacher.stu.s_name}</td>
                                                    <td>${teacher.stu.s_birth}</td>
                                                    <td>${teacher.stu.s_sex}</td>
                                                    <td>${teacher.stu.s_tel}</td>
                                                    <td>${teacher.stu.s_email}</td>
                                                    <%--<td>${teacher.t_class.class_name}</td>--%>
                                                </tr>
                                            </c:forEach>
                                            </tbody>

                                        </table>
                                    </div>
                                </div>
                            <%--分页--%>
                            <ul class="pagination">
                                <%----%>
                                <c:if test="${pb.currentPage==1}">
                                <li class="disabled">
                                    </c:if>
                                    <c:if test="${pb.currentPage!=1}">
                                <li>
                                    </c:if>
                                    <a href="${pageContext.request.contextPath}/teacher/myClassStu?currentPage=${pb.currentPage-1}&rows=10&sname=${sname}&sid=${sid}" aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </li>
                                <%--totalPage=总页码 currentPage=当前页码 startIndex=开始页码--%>
                                <c:forEach begin="1" end="${pb.totalPage}" var="i">
                                    <c:if test="${pb.currentPage == i}">
                                        <li class="active"><a href="${pageContext.request.contextPath}/teacher/myClassStu?currentPage=${i}&rows=10&sname=${sname}&sid=${sid}"> ${i}</a></li>
                                    </c:if>
                                    <c:if test="${pb.currentPage != i}">
                                        <li><a href="${pageContext.request.contextPath}/teacher/myClassStu?currentPage=${i}&rows=10&sname=${sname}&sid=${sid}"> ${i}</a></li></li>
                                    </c:if>
                                </c:forEach>
                                <%----%>
                                <c:if test="${pb.currentPage==pb.totalPage}">
                                <li class="disabled">
                                    </c:if>
                                    <c:if test="${pb.currentPage!=pb.totalPage}">
                                <li>
                                    </c:if>
                                    <a href="${pageContext.request.contextPath}/teacher/myClassStu?currentPage=${pb.currentPage+1}&rows=10&sname=${sname}&sid=${sid}" aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </li>
                                <%----%>
                                <span style="font-size: 25px;margin-left: 5px">
                                    共${pb.totalCount}条记录，共${pb.totalPage}页
                                </span>
                            </ul>

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
