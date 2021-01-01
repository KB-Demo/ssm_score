<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh">
<head>
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
                        <li class="nav-item active"> <a href="${pageContext.request.contextPath}/stu/stuScore"><i class="ftsucai-82"></i>个人成绩</a> </li>
                        <li class="nav-item nav-item-has-subnav">
                            <a href="javascript:void(0)"><i class="ftsucai-edit-2"></i>个人信息编辑</a>
                            <ul class="nav nav-subnav">
                                <li> <a href="${pageContext.request.contextPath}/stu/stuPersonal">个人信息</a> </li>
                                <li> <a href="${pageContext.request.contextPath}/stu/toUpdateStu">修改</a> </li>
                            </ul>
                        <li class="nav-item nav-item-has-subnav active open">
                        <a href="javascript:void(0)"><i class="ftsucai-edit-2"></i>班级成绩排行</a>
                        <ul class="nav nav-subnav">
                            <li class="active"> <a href="${pageContext.request.contextPath}/stu/stuSumRank?currentPage=1&rows=5">总成绩排行</a> </li>
                            <li> <a href="${pageContext.request.contextPath}/stu/queryRankByCname?currentPage=1&rows=5">科目成绩排行</a> </li>
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
                        <span class="navbar-page-title"> 班级成绩排行-总成绩排行 </span>
                    </div>

                    <ul class="topbar-right">
                        <li class="dropdown dropdown-profile">
                            <a href="javascript:void(0)" data-toggle="dropdown">
                                <span>${login_msg}，你好！<span class="caret"></span></span>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-right">
                                <li> <a href="${pageContext.request.contextPath}/stu/toChangePsw"><i class="ftsucai-edit-2"></i> 修改密码</a> </li>
                                <li class="divider"></li>
                                <li><a href="${pageContext.request.contextPath}/stu/exit"><i class="ftsucai-exit2"></i> 退出登录</a> </li>
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
                                    <table class="table table-bordered">
                                        <thead>
                                        <tr>
                                            <th>学号</th>
                                            <th>姓名</th>
                                            <th>平均分</th>
                                            <th>排名</th>
                                        </tr>
                                        </thead>

                                        <tbody>
                                        <c:forEach var="stu" items="${requestScope.get('stus')}" >
                                            <tr>
                                                <td>${stu.s_id}</td>
                                                <td>${stu.s_name}</td>
                                                <td>${stu.score.s_score}</td>
                                                <td>${stu.score.rank}</td>
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
                                       <a href="${pageContext.request.contextPath}/stu/stuSumRank?currentPage=${pb.currentPage-1}&rows=5" aria-label="Previous">
                                           <span aria-hidden="true">&laquo;</span>
                                       </a>
                                    </li>
                                <%--totalPage=总页码 currentPage=当前页码 startIndex=开始页码--%>
                                <c:forEach begin="1" end="${pb.totalPage}" var="i">
                                    <c:if test="${pb.currentPage == i}">
                                        <li class="active"><a href="${pageContext.request.contextPath}/stu/stuSumRank?currentPage=${i}&rows=5"> ${i}</a></li>
                                    </c:if>
                                    <c:if test="${pb.currentPage != i}">
                                        <li><a href="${pageContext.request.contextPath}/stu/stuSumRank?currentPage=${i}&rows=5"> ${i}</a></li></li>
                                    </c:if>
                                </c:forEach>
                                <%----%>
                                <c:if test="${pb.currentPage==pb.totalPage}">
                                    <li class="disabled">
                                </c:if>
                                <c:if test="${pb.currentPage!=pb.totalPage}">
                                    <li>
                                </c:if>
                                    <a href="${pageContext.request.contextPath}/stu/stuSumRank?currentPage=${pb.currentPage+1}&rows=5" aria-label="Next">
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
