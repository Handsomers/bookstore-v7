package com.nick.service;

import com.nick.bean.User;

public interface UserService {
    public void doRegister(User parameterUser) throws Exception;
    public User doLogin(User parmterUser) throws Exception;

    void findByUsername(String username) throws Exception;
}
