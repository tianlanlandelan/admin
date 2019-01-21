package com.yangkaile.admin.common.jdbc;


import com.yangkaile.admin.common.router.MyRouter;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author yangkaile
 * @date 2018-12-08 20:58:35
 */
public class JDBCUtils{
    private static String driver = PropertiesUtil.getProperty("spring.datasource.driverClassName");
    private static String url = PropertiesUtil.getProperty("spring.datasource.url");
    private static String user = PropertiesUtil.getProperty("spring.datasource.username");
    private static String password = PropertiesUtil.getProperty("spring.datasource.password");
//    public static void main(String[] args){
//        Connection conn= getConnection();
//        System.out.println(UpdateUtils.query(conn,MyRouter.class));
//        releaseConnection(conn);
//    }


    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void releaseConnection(Connection conn) {
        try {
            if (conn != null){
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}