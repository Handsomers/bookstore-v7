package com.nick.servlet.model;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.nick.bean.CommonResult;
import com.nick.bean.User;
import com.nick.constants.BookStoreConstants;
import com.nick.service.UserService;
import com.nick.service.impl.UserserviceImpl;
import com.nick.servlet.base.ModelBaseServlet;
import com.nick.utils.JsonUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.runner.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
public class UserServlet extends ModelBaseServlet {
    private UserService userService = new UserserviceImpl();
    //跳转到登录页面
    public void toLoginPage(HttpServletRequest request,HttpServletResponse response) throws  IOException{
        processTemplate("user/login",request,response);
    }
    //处理登录页面
    public void doLogin(HttpServletRequest request,HttpServletResponse response) throws IOException{
        //带数据库的登录校验
        //1、获取客户端登录时的用户名和密码，并封装到user对象中
        Map<String,String[]> parameterMap = request.getParameterMap();
        User parameterUser = new User();
        try{
            BeanUtils.populate(parameterUser,parameterMap);
            //调用业务层的方法处理登录
            User loginUser = userService.doLogin(parameterUser);
            //想要在多个动态资源之间共享loginUser对象
            //将loginUser对象存储到session域对象中
            HttpSession session = request.getSession();
            session.setAttribute(BookStoreConstants.LOGINUSER_SESSIONKEY,loginUser);
            //说明登录成功，跳转到login_success.html页面
            processTemplate("user/login_success",request,response);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            //返回失败信息
            request.setAttribute("errorMsg",e.getMessage());
            //说明登录失败，跳转到login.html页面
            processTemplate("user/login",request,response);
        }
    }
    //跳转到注册页面
    public void toRegisterPage(HttpServletRequest request,HttpServletResponse response) throws IOException {
        processTemplate("user/regist",request,response);
    }
    //处理注册功能
    public void doRegister(HttpServletRequest request,HttpServletResponse response) throws IOException{
        //使用数据库的注册
        //获取请求参数封装到User中
        Map<String,String[]> parameterMap = request.getParameterMap();
        User paramterUser = new User();
        try{
            BeanUtils.populate(paramterUser,parameterMap);
            //获取客户端驶入的验证码
            String checkCode = parameterMap.get("checkCode")[0];
            //获取ketchaServlet中生存的验证码
            String serverCode = (String) request.getSession().getAttribute("serverCode");
            //开始校验验证码
            if (checkCode.equalsIgnoreCase(serverCode)){
                //调用业务层方法处理注册功能
                userService.doRegister(paramterUser);
                //想要在多个动态资源之间共享registerUser对象
                //将registerUser对象村粗到session对象中
                HttpSession session=request.getSession();
                session.setAttribute(BookStoreConstants.REGISTERUSER_SESSIONKEY,paramterUser);
                //没有出现异常，说明注册成功，跳转到regist_success.html页面
                processTemplate("user/regist_success",request,response);
            }else {
                //验证码错误
                throw new RuntimeException("注册失败，验证码错误！！！");
            }
//            //调用业务方法处理注册功能
//            userService.doRegister(paramterUser);
//            //想要在多个动态资源之间共享registerUser对象
//            //将registerUser对象存储到session域对象中
//            HttpSession session = request.getSession();
//            session.setAttribute(BookStoreConstants.REGISERUSER_SESSIONKEY,paramterUser);
//            //没有出现异常，说明注册成功，跳转到regist_sucess.html页面
//            processTemplate("user/regist_success",request,response);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            //返回失败信息
            request.setAttribute("errorMsg",e.getMessage());
            //注册失败，跳转回regist.html页面
            processTemplate("user/regist",request,response);
        }
    }
    //跳转后台管理
    public void toManagerPage(HttpServletRequest request,HttpServletResponse response) throws IOException {
        processTemplate("manager/manager",request,response);
    }
    public void logout(HttpServletRequest request,HttpServletResponse response) throws IOException{
        //1、立即让本次会话生效
        request.getSession().invalidate();
        //2、跳转到PortalServlet访问首页
        response.sendRedirect(request.getContextPath()+"/index.html");
    }

    public void checkUsername(HttpServletRequest request,HttpServletResponse response) throws IOException{
        CommonResult commonResult = null;
        try{
            //1、获取请求参数username的值
            String username = request.getParameter("username");
            //2、调用业务层的findByUsername()方法校验用户名是否已存在
            userService.findByUsername(username);
            //3、没有异常，表示用户名可用
            commonResult = CommonResult.success();
        }catch (Exception e){
            e.printStackTrace();
            //4、出现异常表示用户名已存在，不可用
            commonResult = CommonResult.error().setMessage(e.getMessage());
        }
        //将CommonResult对象转成json字符串，响应给客户端
        JsonUtils.writeResult(response,commonResult);
    }
}
