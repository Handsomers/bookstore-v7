package com.nick.service.impl;

import com.nick.bean.User;
import com.nick.dao.UserDao;
import com.nick.dao.impl.UserDaoImpl;
import com.nick.service.UserService;
import com.nick.utils.MD5Util;


public class UserserviceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    @Override
    public void doRegister(User parameterUser) throws Exception {
        //调用持久层的方法，检查用户名已经存在
        User dbUser = userDao.findByUsername(parameterUser.getUserName());
        if (dbUser != null){
            //说明用户名已被占用
            throw new RuntimeException("注册失败，用户名已被占用！");
        }

        //说明用户名可用，将对应密码进行MD5加秘
        String encodedPassword = MD5Util.encode(parameterUser.getUserPwd());
        parameterUser.setUserPwd(encodedPassword);
        //调用持久层方法保存注册信息
        userDao.insertUser(parameterUser);
    }

    @Override
    public User doLogin(User parameterUser) throws Exception {
        //校验⽤户名
        User dbUser = userDao.findByUsername(parameterUser.getUserName());
        if(dbUser == null){
            //说明⽤户名错误
            throw new RuntimeException("登陆失败，⽤户名错误！"+parameterUser.getUserName());
        }
        //校验密码
        String encodedPassword = MD5Util.encode(parameterUser.getUserPwd());
        if(!dbUser.getUserPwd().equals(encodedPassword)){
            //说明密码错误
            throw new RuntimeException("登陆失败，密码错误！");
        }
        return dbUser;
    }

    @Override
    public void findByUsername(String username) throws Exception {
        //调用持久层方法根据username查询user
        User user = userDao.findByUsername(username);
        if (user!=null){
            throw new RuntimeException("用户名已存在");
        }
    }
}
