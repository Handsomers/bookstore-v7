package com.nick.dao.impl;

import com.nick.bean.User;
import com.nick.dao.BaseDao;
import com.nick.dao.UserDao;

public class UserDaoImpl extends BaseDao<User> implements UserDao {

    @Override
    public User findByUsername(String username) throws Exception {
        String sql = "select user_id userId,user_name userName,user_pwd userPwd,email from t_user where user_name = ?";
        User user = getBean(User.class,sql,username);
        return user;
    }

    @Override
    public void insertUser(User user) {
        String sql = "insert into t_user(user_name,user_pwd,email) values (?,?,?)";
        update(sql,user.getUserName(),user.getUserPwd(),user.getEmail());

    }
}
