package com.nick.test;

import com.nick.bean.Admin;
import com.nick.bean.User;
import com.nick.dao.AdminDao;
import com.nick.dao.impl.AdminDaoImpl;
import com.nick.utils.MD5Util;
import org.junit.Test;

public class AdminRegist {
    AdminDao adminDao = new AdminDaoImpl();
    @Test
    public void doRegister() throws Exception {
        Admin admin = new Admin(2,"nick123","nick123");
        //调用持久层的方法，检查用户名已经存在
        Admin dbAdmin = adminDao.findByAdminname(admin.getAdminName());
        if (dbAdmin != null){
            //说明用户名已被占用
            System.out.println("注册失败，用户名已被占用！");
        }
        //说明用户名可用，将对应密码进行MD5加秘
        String encodedPassword = MD5Util.encode(admin.getAdminPwd());
//        parameterUser.setUserPwd(encodedPassword);
        admin.setAdminPwd(encodedPassword);
        //调用持久层方法保存注册信息
        adminDao.insertAdmin(admin);
    }
}
