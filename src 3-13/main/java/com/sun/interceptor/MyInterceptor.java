package com.sun.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {

    //再请求处理的方法之前执行
    //如果返回true执行下一个拦截器
    //如果返回false就不执行下一个拦截器
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        System.out.println("uri:" + uri);
        //如果是登录注册页面则放行
        if (uri.contains("login") || uri.contains("toRegistered") || uri.contains("registeredUser")) {
            System.out.println("登录或注册页面，放行");
            return true;
        }
        //如果是以及登录的用户则放行
        if (request.getSession().getAttribute("username") != null) {
            System.out.println("用户已登录，放行");
            return true;
        }
        /*request.setAttribute("login_msg", "尚未登录，请登录！");*/
        System.out.println("未登录,拦截");
        request.getRequestDispatcher("/view/login.jsp").forward(request, response);
        return false;
    }

    //再请求处理方法执行之后执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    //在dispatcherServlet处理后执行,做清理工作.
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
