package com.yangkaile.admin.log.mapper;

import com.yangkaile.admin.log.entity.SMSLog;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface SMSLogMapper {
    String tableName = "log_sms_log";


     @Select("SELECT id, phone, type, templateId, codeStr, minuteStr, result, statusCode, createTime FROM " + tableName + " WHERE id = #{id}")
     SMSLog getById(Integer Id);


 /**
  * 根据手机号查询记录 查询最新发送的没有使用过的验证码
  * @param phone 手机号
  * @return
  */
 @Select("SELECT id, phone, type, templateId, codeStr, minuteStr, result, statusCode, createTime FROM " + tableName + " WHERE phone = #{phone} AND state = 0  ORDER BY createTime DESC LIMIT 1")
     SMSLog getByPhone(String phone);


     @Select("SELECT id, phone, type, templateId, codeStr, minuteStr, result, statusCode, state, createTime FROM " + tableName + " WHERE type = #{type}")
     SMSLog getByType(Integer type);



     @Select("SELECT id, phone, type, templateId, codeStr, minuteStr, result, statusCode, state, createTime FROM " + tableName)
     List<SMSLog> getAll();

     @Insert("INSERT INTO " + tableName + "(phone, type, templateId, codeStr, minuteStr, result, statusCode, state, createTime) VALUES (#{phone}, #{type}, #{templateId}, #{codeStr}, #{minuteStr}, #{result}, #{statusCode}, #{state}, #{createTime})")
     @Options(useGeneratedKeys = true)
     Integer insert(SMSLog smsLog);


 /**
  *修改短信验证码记录 只能将状态修改为已使用，不能修改其他内容
  * @param id
  * @return
  */
 @Update("UPDATE " + tableName + " SET  state=1 WHERE id = #{id}")
     Integer update(Integer id);

}
