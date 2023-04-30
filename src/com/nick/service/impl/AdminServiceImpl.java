package com.nick.service.impl;

import com.nick.bean.Admin;
import com.nick.dao.AdminDao;
import com.nick.dao.impl.AdminDaoImpl;
import com.nick.service.AdminService;
import com.nick.utils.MD5Util;

public class AdminServiceImpl implements AdminService {
    private AdminDao adminDao = new AdminDaoImpl();

    @Override
    public Admin doLogin(Admin paramterAdmin) throws Exception {
        //校验用户名
        Admin dbAdmin = adminDao.findByAdminname(paramterAdmin.getAdminName());
        if (dbAdmin==null){
//            说明用户名错误
            throw new RuntimeException("登录失败，用户名错误!"+paramterAdmin.getAdminName());
        }
        //校验密码
        String encodePassword = MD5Util.encode(paramterAdmin.getAdminPwd());
        if (!dbAdmin.getAdminPwd().equals(encodePassword)){
            //说明密码错误
            throw new RuntimeException("登录失败，密码错误！");
        }
        return dbAdmin;
    }

    @Override
    public void findByAdmin(String adminName) throws Exception {
        //调用持久层方法根据adminName查询admin
        Admin admin = adminDao.findByAdminname(adminName);
        if (admin!=null){
            throw new RuntimeException("用户名已存在！");
        }

    }
}
