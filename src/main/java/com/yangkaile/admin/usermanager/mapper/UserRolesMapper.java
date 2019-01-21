package com.yangkaile.admin.usermanager.mapper;

import com.yangkaile.admin.common.mybatis.MyBaseMapper;
import com.yangkaile.admin.usermanager.entity.UserRoles;
import org.apache.ibatis.annotations.*;

/**
 * @author yangkaile
 * @date 2018-08-19 19:43:35
 */
@Mapper
public interface UserRolesMapper extends MyBaseMapper {
    String tableName = "usermanager_user_roles";

     @Select("SELECT userId, roleId, createTime FROM " + tableName + " WHERE userId = #{userId}")
     UserRoles getByUserId(Integer userId);

     @Select("SELECT userId, roleId, createTime FROM " + tableName + " WHERE roleId = #{roleId}")
     UserRoles getByRoleId(Integer roleId);

     @Insert("INSERT INTO " + tableName + "(userId, roleId, createTime) VALUES (#{userId}, #{roleId}, #{createTime})")
     Integer insert(UserRoles userRoles);

     @Delete("DELETE FROM " + tableName + " WHERE roleId = #{roleId}")
     Integer deleteByRoleId(Integer roleId);

     @Update("UPDATE " + tableName + " SET roleId = #{roleId} , createTime = #{createTime} WHERE userId = #{userId}")
     Integer update(UserRoles userRoles);

}
