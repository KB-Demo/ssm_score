<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <title>学生成绩系统-学生信息</title>
    <link rel="icon" href="${APP_PATH}/static/images/favicon.ico" type="image/ico">
    <link href="${APP_PATH}/static/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="${APP_PATH}/static/css/fonts.css"type="text/css" rel="stylesheet">
    <link href="${APP_PATH}/static/css/style.css"type="text/css" rel="stylesheet">
    <script>
        function deleteStu(sid){
            //用户安全提示
            if (confirm("你确定要删除该学生吗？,删除后会连带成绩记录一起删除。")) {
                location.href="${pageContext.request.contextPath}/admin/deleteStu?sid="+sid;
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
                        <li class="nav-item active"> <a href="${pageContext.request.contextPath}/admin/stuPerson"><i class="ftsucai-82"></i>学生信息</a> </li>
                        <li class="nav-item "> <a href="${pageContext.request.contextPath}/admin/teacherPerson"><i class="ftsucai-82"></i>教师信息</a> </li>
                        <li class="nav-item "> <a href="${pageContext.request.contextPath}/admin/courses"><i class="ftsucai-82"></i>课程信息</a> </li>
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
                        <span class="navbar-page-title"> 学生信息 </span>
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
                                <%--添加学生--%>
                                <a class="btn btn-primary m-r-5" href="${pageContext.request.contextPath}/admin/toAddStu"> 添加学生</a>
                                <%--导出excel--%>
                                <a class="btn btn-success m-r-5" href="${pageContext.request.contextPath}/teacher/classStuSumRankInfo?totalCount=${pb.totalCount}&sid=${sid}&sname=${sname}"> 导出Excel</a>
                                <%--按课程名称查询--%>
                                <form class="form-inline pull-right" action="${pageContext.request.contextPath}/admin/stuPerson" method="post" >
                                    <span style="color: red;font-weight:bold">${pb.error}</span>
                                    <div class="form-group">
                                        <input class="form-control" type="text" name="sid" placeholder="请输入学号..">
                                    </div>
                                    <div class="form-group">
                                        <input class="form-control" type="text"  name="sname" placeholder="请输入姓名..">
                                    </div>
                                    <div class="form-group">
                                        <input class="form-control" type="text"  name="class_name" placeholder="请输入班级..">
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
                                            <th>电话号码</th>
                                            <th>邮箱</th>
                                            <th>班级</th>
                                            <th>账号</th>
                                            <th>密码</th>
                                            <th>操作</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="stu" items="${requestScope.get('stus')}" >
                                            <tr>
                                                <td>${stu.s_id}</td>
                                                <td>${stu.s_name}</td>
                                                <td>${stu.s_birth}</td>
                                                <td>${stu.s_sex}</td>
                                                <td>${stu.s_tel}</td>
                                                <td>${stu.s_email}</td>
                                                <td>${stu.s_class.class_name}</td>
                                                <td>${stu.s_userName}</td>
                                                <td>${stu.s_psw}</td>
                                                <td> <a class="btn btn-xs btn-default" href="${pageContext.request.contextPath}/admin/toUpdateStu?s_id=${stu.s_id}&s_name=${stu.s_name}&s_birth=${stu.s_birth}&s_sex=${stu.s_sex}&s_tel=${stu.s_tel}&s_email=${stu.s_email}&class_name=${stu.s_class.class_name}&class_id=${stu.s_class.class_id}&s_userName=${stu.s_userName}&s_psw=${stu.s_psw}" title="编辑" data-toggle="tooltip"><i class="ftsucai-edit-2"></i></a>
                                                    <a class="btn btn-xs btn-default" href="javascript:deleteStu(${stu.s_id});" title="删除" data-toggle="tooltip"><i class="ftsucai-del-2"></i></a></td>
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
                                    <a href="${pageContext.request.contextPath}/admin/stuPerson?currentPage=${pb.currentPage-1}&rows=10&sname=${sname}&sid=${sid}&class_name=${class_name}" aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </li>
                                <%--totalPage=总页码 currentPage=当前页码 startIndex=开始页码--%>
                                <c:forEach begin="1" end="${pb.totalPage}" var="i">
                                    <c:if test="${pb.currentPage == i}">
                                        <li class="active"><a href="${pageContext.request.contextPath}/admin/stuPerson?currentPage=${i}&rows=10&sname=${sname}&sid=${sid}&class_name=${class_name}"> ${i}</a></li>
                                    </c:if>
                                    <c:if test="${pb.currentPage != i}">
                                        <li><a href="${pageContext.request.contextPath}/admin/stuPerson?currentPage=${i}&rows=10&sname=${sname}&sid=${sid}&class_name=${class_name}"> ${i}</a></li></li>
                                    </c:if>
                                </c:forEach>
                                <%----%>
                                <c:if test="${pb.currentPage==pb.totalPage}">
                                <li class="disabled">
                                    </c:if>
                                    <c:if test="${pb.currentPage!=pb.totalPage}">
                                <li>
                                    </c:if>
                                    <a href="${pageContext.request.contextPath}/admin/stuPerson?currentPage=${pb.currentPage+1}&rows=10&sname=${sname}&sid=${sid}&class_name=${class_name}" aria-label="Next">
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
