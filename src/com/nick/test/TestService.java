package com.nick.test;

import com.nick.bean.User;
import com.nick.service.UserService;
import com.nick.service.impl.UserserviceImpl;
import org.junit.Test;

public class TestService {
    @Test
    public void testDoRegister(){
        User parameterUser = new User();
        parameterUser.setUserName("aobama");
        parameterUser.setUserPwd("123456");
        parameterUser.setEmail("123@qq.com");
        UserService userService = new UserserviceImpl();
        try{
            //说明注册成功
            userService.doRegister(parameterUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
