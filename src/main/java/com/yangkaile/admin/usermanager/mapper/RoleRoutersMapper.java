package com.yangkaile.admin.usermanager.mapper;

import com.yangkaile.admin.usermanager.entity.RoleRouters;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleRoutersMapper {
    String tableName = "usermanager_role_routers";
    String userRoles = "usermanager_user_roles";

    @Select("SELECT  roleId, routerId, createTime FROM " + tableName + " WHERE roleId = #{roleId}")
    List<RoleRouters> getByRoleId(Integer roleId);

    @Select("SELECT  roleId, routerId, createTime FROM " + tableName + " WHERE routerId = #{routerId}")
    List<RoleRouters> getByRouterId(Integer routerId);


    @Insert("INSERT INTO " + tableName + "(roleId, routerId, createTime) VALUES (#{roleId}, #{routerId}, #{createTime})")
    Integer insert(RoleRouters roleRouters);

    @Delete("DELETE FROM " + tableName + " WHERE roleId = #{roleId}")
    Integer deleteByroleId(Integer roleId);

    @Select({"SELECT a.roleId,a.routerId FROM " + tableName + " a," + userRoles + " b WHERE a.roleId = b.roleId AND b.userId = #{userId}"})
    List<RoleRouters> getByUserId(Integer userId);
}