package com.lrm.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
    //登录过滤器
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        HttpSession session =request.getSession();
        Object userInfo = session.getAttribute("user");

        //如果Session为空没有user重定向到登录页
        if(userInfo == null){
            response.sendRedirect("/admin");

            return false;
        }
        return true;


    }
}
