package com.nick.servlet.model;

import com.nick.bean.Book;
import com.nick.service.BookService;
import com.nick.service.impl.BookServiceImpl;
import com.nick.servlet.base.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PortalServlet extends ViewBaseServlet {
    BookService bookService = new BookServiceImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //获取所有书籍
            List<Book> bookList = bookService.getBookList();
            //将所有图书存储到请求域对象list
            request.setAttribute("list",bookList);
            //解析模板
            processTemplate("index", request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}