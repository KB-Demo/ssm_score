<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <title>学生成绩系统-教师信息</title>
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
                        <li class="nav-item active"> <a href="${pageContext.request.contextPath}/admin/teacherPerson"><i class="ftsucai-82"></i>教师信息</a> </li>
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
                        <span class="navbar-page-title"> 教师信息-添加教师 </span>
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

                            <div class="card-body">

                                <div class="table-responsive">

                                    <%--
                                     出现的问题：提交sql请求，修改失败，初次考虑，是事务问题，使用aop横切配置事务后依然失败
                                     修改id需要使用隐藏域hidden把id提交
                                    --%>

                                    <div class="card">
                                        <div class="card-header"><h4>添加教师</h4></div>
                                        <div class="card-body">

                                            <form action="${pageContext.request.contextPath}/admin/addTeacher" method="post">
                                                <%-- 隐藏域ID--%>
                                                <%--<input type="hidden" name="s_id" value="null">--%>
                                                <div class="form-group">
                                                    <label for="example-nf-name">姓名</label>
                                                    <input class="form-control" name="t_name" type="text" id="example-nf-name" required="required">
                                                </div>
                                                <div class="form-group">
                                                    <label for="example-nf-birth">出生日期</label>
                                                    <input class="form-control" name="t_birth" type="date" id="example-nf-birth" required="required">
                                                </div>
                                                <div class="form-group">
                                                    <label>性别</label>
                                                    <div class="example-box">
                                                        <label class="ftdms-radio radio-inline radio-primary">
                                                            <input type="radio" name="t_sex" value="男" checked="checked"><span>男</span>
                                                        </label>
                                                        <label class="ftdms-radio radio-inline radio-primary">
                                                            <input type="radio" name="t_sex" value="女"><span>女</span>
                                                        </label>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="example-nf-tel">电话</label>
                                                    <input class="form-control" name="t_tel" type="text" id="example-nf-tel"required="required">
                                                </div>
                                                <div class="form-group">
                                                    <label for="example-nf-email">邮箱</label>
                                                    <input class="form-control" name="t_email" type="email" id="example-nf-email" required="required">
                                                </div>
                                                <span style="color: red;font-weight:bold">${error}</span>
                                                <div class="form-group">
                                                    <label for="example-nf-username">用户名</label>
                                                    <input class="form-control" name="t_userName" type="text" id="example-nf-username" required="required">
                                                </div>
                                                <div class="form-group">
                                                    <label for="example-nf-psw">密码</label>
                                                    <input class="form-control" name="t_psw" type="text" id="example-nf-psw" required="required">
                                                </div>
                                                <div class="example-box" style="text-align: center">
                                                    <input class="btn btn-success btn-w-md" type="submit" value="提交" />
                                                    <input class="btn btn-warning" type="reset" value="重置" />
                                                    <a href="${pageContext.request.contextPath}/admin/teacherPerson"><input class="btn btn-warning" type="button" value="返回"></a>
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
<script type="text/javascript" src="${APP_PATH}/static//js/baidu.js"></script>
</body>
</html>
