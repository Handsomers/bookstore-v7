package com.nick.service;

import com.nick.bean.Admin;

public interface AdminService {
    public Admin doLogin(Admin paramterAdmin) throws Exception;
    void findByAdmin(String adminName) throws Exception;
}
