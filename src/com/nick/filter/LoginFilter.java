package com.nick.filter;


import com.nick.bean.Admin;
import com.nick.bean.CommonResult;
import com.nick.bean.User;
import com.nick.constants.BookStoreConstants;
import com.nick.utils.JsonUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class LoginFilter implements Filter {
    @Override
    public void destroy() {
    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //1. 判断⽤户是否已登录:就是判断session中是否存储了User对象
        //将req强转成HttpServletRequest类型
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        User user = (User) request.getSession().getAttribute(BookStoreConstants.LOGINUSER_SESSIONKEY);
        Admin admin = (Admin) request.getSession().getAttribute(BookStoreConstants.LOGINADMIN_SESSIONKEY);
        System.out.println(admin);
        if (user == null && admin==null) {
            //当前未登录
            //想办法让⽤户跳转到登录⻚⾯login.html
            //获取请求参数method的值
            String method = request.getParameter("method");
            //1. 如果是同步请求:method=toCartPage、method=cleanCart
            if (method.equals("toCartPage") || method.equals("cleanCart") || method.equals("checkout")) {
                //直接重定向跳转到UserServlet调⽤toLoginPage⽅法
                response.sendRedirect(request.getContextPath()+"/user?method=toLoginPage");
            } else if (method.equals("toMangerPage")) {
                response.sendRedirect(request.getContextPath()+"/admin?method=toAdminLoginPage");
            } else {
                //2. 其它的都是异步请求
                CommonResult commonResult = CommonResult.error().setMessage("unLogin");
                JsonUtils.writeResult(response, commonResult);
            }
            return;
        }
        //如果没有⾛到那个if判断，说明当前已登录，那么就直接放⾏
        chain.doFilter(req, resp);
    }
    @Override
    public void init(FilterConfig config) throws ServletException {
    }
}