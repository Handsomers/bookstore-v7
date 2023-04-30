package com.nick.test;

import com.nick.bean.User;
import com.nick.dao.BaseDao;
import com.nick.utils.JDBCUtil;
import org.junit.Test;

import java.sql.Connection;

public class TestDao extends BaseDao<User> {
    @Test
    public void testGetConnection(){
        User user  = new User(1,"nick","12345","12");
        String sql = "insert into t_user(user_name,user_pwd,email) values (?,?,?)";
        update(sql,user.getUserName(),user.getUserPwd(),user.getEmail());
    }
}
