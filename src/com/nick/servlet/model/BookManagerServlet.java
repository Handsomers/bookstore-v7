package com.nick.servlet.model;

import com.nick.bean.Book;
import com.nick.service.BookService;
import com.nick.service.impl.BookServiceImpl;
import com.nick.servlet.base.ModelBaseServlet;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class BookManagerServlet extends ModelBaseServlet {
    BookService bookService = new BookServiceImpl();

    //跳转到后台
    public void toManagerPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processTemplate("manager/manager",request,response);
    }

    //查询所有的图书列表，并且展示到book_manager.html⻚⾯
    public void showBookList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //response.getWriter().write("showBookList...");
        //查询所有图书列表
        try{
            List<Book> booklist = bookService.getBookList();
            request.setAttribute("bookList", booklist);
            processTemplate("manager/book_manager", request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void orderManger(HttpServletRequest request,HttpServletResponse response) throws IOException{
        processTemplate("manager/order_manager",request,response);
    }
//    跳转添加图书页面
    void toAddPage(HttpServletRequest request,HttpServletResponse response) throws IOException{
        processTemplate("manager/book_edit",request,response);
    }

    /**
     * 添加或者图书信息
     * @param request
     * @param response
     * @throws IOException
     */
    public void saveOrUpdateBook(HttpServletRequest request,HttpServletResponse response) throws IOException{
        //1、获取请求参数
        Map<String,String[]>parameterMap = request.getParameterMap();
        //2、将parameterMap中的数据封装到Book对象
        try{
            Book book = new Book();
            BeanUtils.populate(book,parameterMap);
            bookService.saveOrUpdateBook(book);
            //保存成功，则重新查询所有书籍
            response.sendRedirect(request.getContextPath()+"/protected/bookManager?method=showBookList");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //删除图书
    public void removeBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1. 获取要删除的图书的id
        Integer id = Integer.valueOf(request.getParameter("id"));
        //2. 调⽤业务层的⽅法根据id删除图书
        try {
            bookService.removeBook(id);
            //3. 删除成功，重新查询所有图书信息
            response.sendRedirect(request.getContextPath()+"/protected/bookManager?method=showBookList");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //跳转到修改页面
    public void toEditPage(HttpServletRequest request,HttpServletResponse response) throws IOException{
        //获取客户端传入的id
        Integer id = Integer.valueOf(request.getParameter("id"));
        try{
            //根据id查询图书详情
            Book book = bookService.getBookById(id);
            //将图书信息存储到请求域
            request.setAttribute("book",book);
            processTemplate("manager/book_edit",request,response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 添加或者图书信息
     * @param request
     * @param response
     * @throws IOException
     */
//    public void saveOrUpdateBook(HttpServletRequest request,HttpServletResponse response) throws IOException{
//        //1、获取请求参数
//        Map<String,String[]> parameterMap = request.getParameterMap();
//        //2、将parameterMap中的数据封装到Book对象
//        try{
//            Book book = new Book();
//            BeanUtils.populate(book,parameterMap);
//            bookService.saveOrUpdateBook(book);
//            //保存成功，则重新查询所有图书
//            response.sendRedirect(request.getContextPath()+"/bookManager?method=showBookList");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
