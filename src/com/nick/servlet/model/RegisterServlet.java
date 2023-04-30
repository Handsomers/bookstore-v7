package com.nick.servlet.model;

import com.nick.bean.User;
import com.nick.service.UserService;
import com.nick.service.impl.UserserviceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class RegisterServlet extends HttpServlet {
    private UserService userService = new UserserviceImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

//        //不使用数据库的注册
//        String username = request.getParameter("username");
//        System.out.println(username);
//        //假定username等于happy表明已被注册
//        if("happy".equals(username)){
//            response.sendRedirect(request.getContextPath()+"/pages/user/regist.html");
//        }else {
//            response.sendRedirect(request.getContextPath()+"/pages/user/regist_success.html");
//        }
        //使用数据库注册
        //获取请求参数封装到User中
        Map<String,String[]> parameterMap = request.getParameterMap();
        User parameterUser = new User();

        try{
            BeanUtils.populate(parameterUser,parameterMap);
            //调用业务层方法处理注册功能
            System.out.println(parameterUser);
            userService.doRegister(parameterUser);
            //没有出现异常，说明注册成功，跳转到regist_success.html
            response.sendRedirect(request.getContextPath()+"/pages/user/regist_success.html");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            //注册失败，跳转回regist.html页面
            response.sendRedirect(request.getContextPath()+"/pages/user/regist.html");
        }
    }
}
