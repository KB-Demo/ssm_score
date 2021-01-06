<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <title>学生成绩系统-总成绩排行</title>
    <link rel="icon" href="${APP_PATH}/static/images/favicon.ico" type="image/ico">
    <link href="${APP_PATH}/static/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="${APP_PATH}/static/css/fonts.css"type="text/css" rel="stylesheet">
    <link href="${APP_PATH}/static/css/style.css"type="text/css" rel="stylesheet">
    <script>
        function deleteStu(sid,cname){
            //用户安全提示
            if (confirm("你确定要删除吗？")) {
                location.href="${pageContext.request.contextPath}/teacher/deleteStuScoreById?sid="+sid+"&cname="+cname;
            }
        }

        function updateStuScore(teacher) {
            $.ajax({
                type:"POST",
                url:"${pageContext.request.contextPath}/teacher/toUpdateStuScore",
                data:'teacher='+teacher,
                /*dataType:"json"*/
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
                        <span class="navbar-page-title"> 成绩管理-课程学生成绩 </span>
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
                            <%--class="pull-right search-bar"--%>
                            <div class="card-toolbar clearfix">
                                <%--添加学生成绩--%>
                                <a class="btn btn-primary m-r-5" href="${pageContext.request.contextPath}/teacher/toAddStuScore"> 添加学生成绩</a>
                                <%--导出excel--%>
                                <a class="btn btn-success m-r-5" href="${pageContext.request.contextPath}/teacher/courseStuRankInfo?totalCount=${pb.totalCount}&sid=${sid}&sname=${sname}&cname=${cname}"> 导出Excel</a>
                                <%--按课程名称查询--%>
                                <form class="form-inline pull-right" action="${pageContext.request.contextPath}/teacher/courseStuScore" method="post" >
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
                                        <%--<label class="sr-only" for="example-if-password">密码</label>--%>
                                        <input class="form-control" type="text"  name="cname" placeholder="请输入课程名称..">
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
                                            <th>课程</th>
                                            <th>分数</th>
                                            <th>排名</th>
                                            <th>班级</th>
                                            <th>操作</th>
                                        </tr>
                                        </thead>

                                        <tbody>
                                        <c:forEach var="teacher" items="${requestScope.get('teachers')}" >
                                            <tr>
                                                <td>${teacher.stu.s_id}</td>
                                                <td>${teacher.stu.s_name}</td>
                                                <td>${teacher.course.c_name}</td>
                                                <td>${teacher.score.s_score}</td>
                                                <td>${teacher.score.rank}</td>
                                                <td>${teacher.t_class.class_name}</td>
                                                <td> <a class="btn btn-xs btn-default" href="${pageContext.request.contextPath}/teacher/toUpdateStuScore?sid=${teacher.stu.s_id}&sname=${teacher.stu.s_name}&cname=${teacher.course.c_name}&score=${teacher.score.s_score}&rank=${teacher.score.rank}&class_name=${teacher.t_class.class_name}" title="编辑" data-toggle="tooltip"><i class="ftsucai-edit-2"></i></a>
                                                    <a class="btn btn-xs btn-default" href="javascript:deleteStu('${teacher.stu.s_id}','${teacher.course.c_name}');" title="删除" data-toggle="tooltip"><i class="ftsucai-del-2"></i></a></td>
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
                                    <a href="${pageContext.request.contextPath}/teacher/courseStuScore?currentPage=${pb.currentPage-1}&rows=10&sname=${sname}&sid=${sid}&cname=${cname}" aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </li>
                                <%--totalPage=总页码 currentPage=当前页码 startIndex=开始页码--%>
                                <c:forEach begin="1" end="${pb.totalPage}" var="i">
                                    <c:if test="${pb.currentPage == i}">
                                        <li class="active"><a href="${pageContext.request.contextPath}/teacher/courseStuScore?currentPage=${i}&rows=10&sname=${sname}&sid=${sid}&cname=${cname}"> ${i}</a></li>
                                    </c:if>
                                    <c:if test="${pb.currentPage != i}">
                                        <li><a href="${pageContext.request.contextPath}/teacher/courseStuScore?currentPage=${i}&rows=10&sname=${sname}&sid=${sid}&cname=${cname}"> ${i}</a></li></li>
                                    </c:if>
                                </c:forEach>
                                <%----%>
                                <c:if test="${pb.currentPage==pb.totalPage}">
                                <li class="disabled">
                                    </c:if>
                                    <c:if test="${pb.currentPage!=pb.totalPage}">
                                <li>
                                    </c:if>
                                    <a href="${pageContext.request.contextPath}/teacher/courseStuScore?currentPage=${pb.currentPage+1}&rows=10&sname=${sname}&sid=${sid}&cname=${cname}" aria-label="Next">
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
