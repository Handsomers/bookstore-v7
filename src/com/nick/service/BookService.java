package com.nick.service;

import com.nick.bean.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookService {
    List<Book> getBookList() throws Exception;
    public void removeBook(Integer bookId) throws Exception;

    public void saveOrUpdateBook(Book book) throws Exception;

    Book getBookById(Integer id) throws Exception;
}
