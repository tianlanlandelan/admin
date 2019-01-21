package com.yangkaile.admin.log.mapper;

import com.yangkaile.admin.log.entity.EmailLog;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmailLogMapper {
    String tableName = "log_email_log";

 /**
  * 根据邮箱，查询最新的一条可用的验证码
  * @param email
  * @return
  */
 @Select(" SELECT id,type,email,title,content,code,result,statusCode,createTime " +
            " FROM " + tableName +
            " WHERE email = #{email} AND statusCode = 0 AND state = 0 " +
            " ORDER BY createTime DESC LIMIT 1")
 EmailLog getByEmail(String email);

 /**
  * 将验证码状态修改为已使用
  * @param id
  * @return
  */
 @Update("UPDATE " + tableName + " SET state = 1,updateTime = sysdate() WHERE id = #{id}")
 Integer update(Integer id);

 /**
  * 添加验证码
  * @param emailLog
  * @return
  */
 @Insert(" INSERT INTO " + tableName + "(type, email, title, content, code,result,statusCode,state,createTime) " +
         " VALUES (#{type}, #{email}, #{title}, #{content}, #{code},#{result},#{statusCode},#{state},#{createTime})")
 @Options(useGeneratedKeys = true)
 Integer insert(EmailLog emailLog);


     @Select("SELECT id, type, recipients, title, content, sendDate FROM " + tableName + " WHERE id = #{id}")
     EmailLog getById(Integer Id);


     @Select("SELECT id, type, recipients, title, content, sendDate FROM " + tableName + " WHERE type = #{type}")
     EmailLog getByType(String type);


     @Select("SELECT id, type, recipients, title, content, sendDate FROM " + tableName + " WHERE recipients = #{recipients}")
     EmailLog getByRecipients(String recipients);


     @Select("<script>" +
             " SELECT * " +
             " FROM " +
             tableName +
             " <where> " +
             "<if test=\"com.yangkaile.admin.serviceregistycenter.entity != null and com.yangkaile.admin.serviceregistycenter.entity.type != null\"> AND type= #{com.yangkaile.admin.serviceregistycenter.entity.type}  </if>" +
             "<if test=\"com.yangkaile.admin.serviceregistycenter.entity != null and com.yangkaile.admin.serviceregistycenter.entity.title != null\"> AND title= #{com.yangkaile.admin.serviceregistycenter.entity.title}  </if>" +
             "<if test=\"com.yangkaile.admin.serviceregistycenter.entity != null and com.yangkaile.admin.serviceregistycenter.entity.recipients != null\"> AND recipients= #{com.yangkaile.admin.serviceregistycenter.entity.recipients}  </if>" +
             "<if test=\"startDate != null\"> AND sendDate &gt; #{startDate}  </if>" +
             "<if test=\"endDate != null\"> AND sendDate  &lt; #{endDate}  </if>" +
             " </where> " +
             " LIMIT  #{offset},#{rows}" +
             "</script>"
     )
     List<EmailLog> getListByCondition(Map params);


     @Select("SELECT id, type, recipients, title, content, sendDate FROM " + tableName)
     List<EmailLog> getAll();



}
