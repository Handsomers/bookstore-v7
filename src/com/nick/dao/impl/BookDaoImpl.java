package com.nick.dao.impl;

import com.nick.bean.Book;
import com.nick.dao.BaseDao;
import com.nick.dao.BookDao;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class BookDaoImpl  extends BaseDao<Book> implements BookDao{

    @Override
    public List<Book> selectBookList() throws SQLException {
        String sql = "select book_id bookId,book_name bookName,author,price,sales,stock,img_path imgPath,tag from t_book";
        return getBeanList(Book.class,sql);
    }

    @Override
    public void deleteBook(Integer bookId) throws SQLException {
        String sql = "delete from t_book where book_id=?";
        update(sql,bookId);
    }

    @Override
    public void insertBook(Book book) throws SQLException {
//        String sql="insert into t_book book_name,author,price,sales,stock,img_path values (?,?,?,?,?,?)";
        String sql = "insert into t_book (book_name,author,price,sales,stock,img_path,tag) values (?,?,?,?,?,?,?)";
        update(sql,book.getBookName(),book.getAuthor(),book.getPrice(),book.getSales(),book.getStock(),book.getImgPath(),book.getTag());
    }

    @Override
    public Book selectBookByPrimaryKey(Integer bookId) throws SQLException{
        String sql="select book_id bookId,book_name bookName,author,price,sales,stock,img_path imgPath,tag from t_book where book_id=?";
        return getBean(Book.class,sql,bookId);
    }

    @Override
    public void updateBook(Book book) throws SQLException {
        //我们暂时不修改图⽚路径
        String sql = "update t_book set book_name=?,price=?,author=?,sales=?,stock=?,tag=? where book_id=?";

        update(sql,book.getBookName(),book.getPrice(),book.getAuthor(),book.getSales(),book.getStock(),book.getBookId(),book.getTag());
    }

    @Override
    public void updateBookArr(Object[][] bookArrParam) {
        String sql = "update t_book set sales=sales+?,stock=stock-? where book_id=?";
        batchUpdate(sql,bookArrParam);
    }


}
