package com.nick.filter;

import com.nick.utils.JDBCUtil;

import javax.servlet.*;
import java.io.IOException;
public class TransactionFilter implements Filter {
    @Override
    public void destroy() {
    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {
        try {
            //开启事务
            JDBCUtil.startTransaction();
            chain.doFilter(req, resp);
            //没有出现异常，则提交事务
            JDBCUtil.commit();
        } catch (Exception e) {
            e.printStackTrace();
            //回滚事务
            JDBCUtil.rollback();
        }
    }
    @Override
    public void init(FilterConfig config) throws ServletException {
    }
}
