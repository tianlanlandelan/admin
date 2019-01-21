package com.yangkaile.admin.usermanager.mapper;

import com.yangkaile.admin.common.mybatis.MyBaseMapper;

import com.yangkaile.admin.usermanager.entity.User;
import com.yangkaile.admin.usermanager.view.UserView;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * UserMapper继承自MyBaseMapper
 * @author yangkaile
 * @date 2018-11-29 19:51:15
 */
@Mapper
public interface UserMapper extends MyBaseMapper {
    String tableName = "usermanager_user";
    String userRoles = "usermanager_user_roles";

     @Insert("INSERT INTO " + tableName + "(userName, phone, wxId, email, password, createTime, mask,isDelete) "
             + " VALUES (#{userName}, #{phone}, #{wxId}, #{email}, #{password}, #{createTime}, #{mask},#{isDelete})")
     @Options(useGeneratedKeys = true)
     Integer insert(User user);

     @Update("UPDATE " + tableName
             + " SET userName=#{userName}, phone=#{phone}, wxId=#{wxId}, email=#{email}, password=#{password}, createTime=#{createTime}, mask=#{mask} "
             + "WHERE id = #{id}")
     Integer update(User user);

     @Select("SELECT id, userName, phone, wxId, email, password, createTime, mask,isDelete FROM " + tableName
             + " WHERE userName = #{userName} AND isDelete = 0")
     User getByUserName(String userName);

     @Select("SELECT id, userName, phone, wxId, email, password, createTime, mask,isDelete FROM " + tableName
             + " WHERE phone = #{phone} AND isDelete = 0")
     User getByPhone(String phone);

     @Select("SELECT id, userName, phone, wxId, email, password, createTime, mask,isDelete FROM " + tableName
             + " WHERE wxId = #{wxId} AND isDelete = #{isDelete}")
     User getByWXId(User user);

     @Select("SELECT id, userName, phone, wxId, email, password, createTime, mask,isDelete FROM " + tableName
             + " WHERE email = #{email} AND isDelete = 0")
     User getByEmail(String email);

     @Select("SELECT b.id, b.userName, b.phone, b.createTime, b.email, b.createTime, b.mask, b.isDelete "
             + " FROM " + userRoles + " a, "+ tableName + " b "
             + " WHERE a.userId =b.id  AND a.roleId = #{roleId}" )
     List<User> getUsersByRoleId(Integer roleId);

     @Select("select u.id as userId,u.userName as userName,r.name as roleName,u.createTime as createTime from user u,user_roles ur,role r where u.id = ur.userId and r.id = ur.roleId")
     List<UserView> getAllUserNameAndRoleName();
}
