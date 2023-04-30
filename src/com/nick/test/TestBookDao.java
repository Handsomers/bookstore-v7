package com.nick.test;

import com.nick.dao.BookDao;
import com.nick.dao.impl.BookDaoImpl;
import org.junit.Test;

import java.sql.SQLException;

public class TestBookDao{

    @Test
    public void testBookDao(){
        BookDao bookDao = new BookDaoImpl();
        try {
            System.out.println(bookDao.selectBookList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
