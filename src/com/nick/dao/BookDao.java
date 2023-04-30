package com.nick.dao;

import com.nick.bean.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookDao {
    public List<Book> selectBookList() throws SQLException;

    void deleteBook(Integer bookId) throws SQLException;

    void insertBook(Book book) throws SQLException;

    public Book selectBookByPrimaryKey(Integer bookId) throws SQLException;

    void updateBook(Book book) throws SQLException;

    void updateBookArr(Object[][] bookArrParam);
}
