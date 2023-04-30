package com.nick.test;

import com.nick.dao.AdminDao;
import com.nick.dao.impl.AdminDaoImpl;
import org.junit.Test;

import java.sql.SQLException;

public class TestAdminDao {
    @Test
    public void Test() throws SQLException {
        String name = "nick123";
        AdminDao adminDao =  new AdminDaoImpl();
        System.out.println(adminDao.findByAdminname(name));
    }

}
