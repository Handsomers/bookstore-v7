package com.nick.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 包名:com.atguigu.jdbc.utils
 *
 * @author Leevi
 * 日期2021-04-29  15:15
 * 这个工具类中会提供仨方法:
 * 1. 获取连接池对象
 * 2. 从连接池中获取连接
 * 3. 将链接归还到连接池
 */
public class JDBCUtil {
    private static DataSource dataSource;
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    static {
        try {
            //1. 使⽤类加载器读取配置⽂件，转成字节输⼊流
            InputStream is = JDBCUtil.class.getClassLoader().getResourceAsStream("druid.properties");
            //2. 使⽤Properties对象加载字节输⼊流
            Properties properties = new Properties();
            properties.load(is);
            //3. 使⽤DruidDataSourceFactory创建连接池对象
            dataSource = DruidDataSourceFactory.createDataSource(properties);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取连接池对象
    public static DataSource getDataSource() {
        return dataSource;
    }

    //获取连接
    public static Connection getConnection() {
        try {
            Connection conn = threadLocal.get();
            if (conn == null) {
                //说明此时ThreadLocal中没有连接
                //从连接池中获取⼀个连接
                conn = dataSource.getConnection();
                //将连接存储到ThreadLocal中
                threadLocal.set(conn);
            }
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());

        }
    }

    //释放链接
    public static void releaseConnection() {
        try {
            if (!getConnection().getAutoCommit()) {
                //先将conn的AutoCommit设置为true
                getConnection().setAutoCommit(true);
            }
            //将conn归还到连接池
            getConnection().close();
            //将conn从ThreadLocal中移除掉
            threadLocal.remove();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    //开启事务
    public static void startTransaction() {
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    //提交事务
    public static void commit() {
        try {
            getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    //回滚事务
    public static void rollback() {
        try {
            getConnection().rollback();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}