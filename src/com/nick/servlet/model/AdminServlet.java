package com.nick.servlet.model;


import com.nick.bean.Admin;
import com.nick.bean.User;
import com.nick.constants.BookStoreConstants;
import com.nick.service.AdminService;
import com.nick.service.impl.AdminServiceImpl;
import com.nick.servlet.base.ModelBaseServlet;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class AdminServlet extends ModelBaseServlet {
    AdminService adminService = new AdminServiceImpl();
//    //跳转到后台
//    public void toManagerPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        processTemplate("manager/manager",request,response);
//    }

    //跳转登录界面
    public void toAdminLoginPage(HttpServletRequest request,HttpServletResponse response) throws IOException{
        processTemplate("admin/admin_login",request,response);
    }
    //处理登录页面
    public void doLogin(HttpServletRequest request,HttpServletResponse response) throws IOException{
        //带数据库的登录校验
        //1、获取客户端登陆时的用户名和密码，并封装到user对象中
        Map<String,String []> paramterMap =request.getParameterMap();
        Admin parameterAdmin = new Admin();
        try {
            BeanUtils.populate(parameterAdmin,paramterMap);
            System.out.println(parameterAdmin);

            //调用业务层的方法处理登录
            Admin loginAdmin =  adminService.doLogin(parameterAdmin);
            //想要在多个动态资源之间共享loginAdmin对象
            //将loginAmind对象存储到session域对象中
            HttpSession session = request.getSession();
            session.setAttribute(BookStoreConstants.LOGINADMIN_SESSIONKEY,loginAdmin);
            //说明登录成功，跳转到后台管理界面
            processTemplate("admin/login_success",request,response);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
            //返回失败信息
            request.setAttribute("errorMsg",e.getMessage());
            //说明登录失败，跳转到login.html页面
            processTemplate("admin/admin_login",request,response);
        }

    }

    public void logout(HttpServletRequest request,HttpServletResponse response) throws IOException{
        //1、立即让本次会话生效
        request.getSession().invalidate();
        //2、跳转到PortalServlet访问首页
        response.sendRedirect(request.getContextPath()+"/index.html");
    }



}
