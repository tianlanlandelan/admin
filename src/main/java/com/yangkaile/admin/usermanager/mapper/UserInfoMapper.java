package com.yangkaile.admin.usermanager.mapper;

import com.yangkaile.admin.common.mybatis.MyBaseMapper;
import com.yangkaile.admin.usermanager.entity.UserInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserInfoMapper  extends MyBaseMapper {
    String tableName = "usermanager_user_info";

     @Insert("INSERT INTO " + tableName + "(id, nickName, birthday, gender, address, signature, userPortrait, email, phone, createTime, mask) VALUES (#{id}, #{nickName}, #{birthday}, #{gender}, #{address}, #{signature}, #{userPortrait}, #{email}, #{phone}, #{createTime}, #{mask})")
     Integer insert(UserInfo userInfo);

     @Delete("DELETE FROM " + tableName + " WHERE id = #{id}")
     Integer deleteById(Integer id);

     @Update("UPDATE " + tableName + " SET nickName=#{nickName}, birthday=#{birthday}, gender=#{gender}, address=#{address}, signature=#{signature}, userPortrait=#{userPortrait}, email=#{email}, phone=#{phone}, createTime=#{createTime}, mask=#{mask} WHERE id = #{id}")
     Integer update(UserInfo userInfo);
}
