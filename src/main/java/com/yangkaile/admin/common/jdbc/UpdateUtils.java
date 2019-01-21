package com.yangkaile.admin.common.jdbc;


import com.yangkaile.admin.common.mybatis.MyBaseEntity;
import com.yangkaile.admin.common.mybatis.MyBaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author yangkaile
 * @date 2018-12-08 21:06:00
 */
public class UpdateUtils {

//    public static <T> int insert(Connection connection, Class<T> clazz, T entity){
//        try {
//            MyBaseEntity baseEntity =  MyBaseUtils.getBaseEntity(clazz);
//            StringBuilder builder = new StringBuilder();
//            builder.append("INSERT INTO ").append(baseEntity.getTableName()).append(" (");
////            builder.append()
//            String sql = "insert into " + baseEntity.getTableName();
//            PreparedStatement preparedStatement=connection.prepareStatement(sql);
//            for(int i = 1; i <= args.length; i++){
//                preparedStatement.setObject(i, args[i]);
//            }
//            preparedStatement.executeUpdate();
//            if(preparedStatement !=null){
//                preparedStatement.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }

    public static void update(Connection connection, String sql,Object ...args){
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            for(int i = 1; i <= args.length; i++){
                preparedStatement.setObject(i, args[i]);
            }
            preparedStatement.executeUpdate();
            if(preparedStatement !=null){
                preparedStatement.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
