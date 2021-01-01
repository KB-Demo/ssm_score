<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <title>学生成绩系统-个人信息修改</title>
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
                        <li class="nav-item nav-item-has-subnav active open">
                            <a href="javascript:void(0)"><i class="ftsucai-edit-2"></i>个人信息编辑</a>
                            <ul class="nav nav-subnav">
                                <li> <a href="${pageContext.request.contextPath}/stu/stuPersonal">个人信息</a> </li>
                                <li class="active"> <a href="${pageContext.request.contextPath}/stu/toUpdateStu">修改</a> </li>
                            </ul>
                        <li class="nav-item nav-item-has-subnav">
                            <a href="javascript:void(0)"><i class="ftsucai-edit-2"></i>班级成绩排行</a>
                            <ul class="nav nav-subnav">
                                <li> <a href="${pageContext.request.contextPath}/stu/stuSumRank?currentPage=1&rows=5">总成绩排行</a> </li>
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
                        <span class="navbar-page-title"> 个人信息编辑-修改 </span>
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
                                    <form action="${pageContext.request.contextPath}/stu/updateStu" method="post">
                                        <%--
                                         出现的问题：提交sql请求，修改失败，初次考虑，是事务问题，使用aop横切配置事务后依然失败
                                         修改id需要使用隐藏域hidden把id提交
                                        --%>

                                                <div class="card">
                                                    <div class="card-header"><h4>修改个人信息</h4></div>
                                                    <div class="card-body">

                                                        <form action="${pageContext.request.contextPath}/stu/updateStu" method="post">
                                                            <%-- 隐藏域ID--%>
                                                            <input type="hidden" name="s_id" value="${stu.s_id}">
                                                            <div class="form-group">
                                                                <label for="example-nf-name">姓名</label>
                                                                <input class="form-control" name="s_name" type="text" id="example-nf-name" value="${stu.s_name}">
                                                            </div>
                                                            <div class="form-group">
                                                                <label for="example-nf-birth">出生日期</label>
                                                                <input class="form-control" name="s_birth" type="date" id="example-nf-birth" value="${stu.s_birth}">
                                                            </div>
                                                            <div class="form-group">
                                                                <label>性别</label>
                                                                <c:if test="${stu.s_sex == '男'}">
                                                                    <div class="example-box">
                                                                        <label class="ftdms-radio radio-inline radio-primary">
                                                                            <input type="radio" name="s_sex" value="男" checked="checked"><span>男</span>
                                                                        </label>
                                                                        <label class="ftdms-radio radio-inline radio-primary">
                                                                            <input type="radio" name="s_sex" value="女"><span>女</span>
                                                                        </label>
                                                                    </div>
                                                                </c:if>
                                                                <c:if test="${stu.s_sex == '女'}">
                                                                    <div class="example-box">
                                                                        <label class="ftdms-radio radio-inline radio-primary">
                                                                            <input type="radio" name="s_sex" value="男" ><span>男</span>
                                                                        </label>
                                                                        <label class="ftdms-radio radio-inline radio-primary">
                                                                            <input type="radio" name="s_sex" value="女" checked="checked"><span>女</span>
                                                                        </label>
                                                                    </div>
                                                                </c:if>
                                                            </div>
                                                            <div class="form-group">
                                                                <label for="example-nf-tel">电话</label>
                                                                <input class="form-control" name="s_tel" type="text" id="example-nf-tel" value="${stu.s_tel}">
                                                            </div>
                                                            <div class="form-group">
                                                                <label for="example-nf-email">电话</label>
                                                                <input class="form-control" name="s_email" type="email" id="example-nf-email" value="${stu.s_email}">
                                                            </div>
                                                            <div class="example-box" style="text-align: center">
                                                                <input class="btn btn-success btn-w-md" type="submit" value="提交" />
                                                                <input class="btn btn-warning" type="reset" value="重置" />
                                                            </div>

                                                        </form>

                                                    </div>
                                                </div>
                                    </form>
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
