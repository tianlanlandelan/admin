package com.yangkaile.admin.usermanager.mapper;

import com.yangkaile.admin.common.mybatis.MyBaseMapper;
import com.yangkaile.admin.usermanager.entity.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * RoleMapper 继承自MyBaseMapper
 * @author yangkaile
 * @date 2018-11-29 19:08:40
 */
@Mapper
public interface RoleMapper extends MyBaseMapper {
    String tableName = "usermanager_role";
    String userRoles = "usermanager_user_roles";
    String roleRouters = "usermanager_role_routers";

 /**
  * 根据角色名查询角色
  * @param role 角色对象
  * @return Role对象
  */
 @Select("SELECT id, name, description, createTime FROM " + tableName + " WHERE name = #{name}")
     Role getByName(Role role);

 /**
  * 查询用户的角色
  * @param userId UserId
  * @return Role
  */
     @Select("SELECT b.id b, b.name , b.description , b.createTime  "
             +" FROM " + userRoles + " a, "+ tableName + " b "
             + " WHERE a.roleId = b.id AND a.userId = #{userId}" )
     Role getRoleByUserId(Integer userId);

 /**
  * 根据权限ID查询角色
  * @param routerId 权限ID
  * @return Role列表
  */
 @Select("SELECT b.id, b.name, b.description, b.createTime "
         +" FROM " + roleRouters + " a, "+ tableName + " b "
         + " WHERE a.roleId = b.id AND a.routerId = #{routerId}" )
     List<Role> getRolesByRouterId(Integer routerId);

 /**
  * 添加角色并返回角色ID
  * @param role Role对象
  * @return insert成功的条数
  */
     @Insert("INSERT INTO " + tableName + "(name, description, createTime) VALUES (#{name}, #{description}, #{createTime})")
     @Options(useGeneratedKeys = true)
     Integer insert(Role role);

 /**
  * 根据id删除角色
  * @param id 角色id
  * @return
  */
 @Delete("DELETE FROM " + tableName + " WHERE id = #{id}")
     Integer deleteById(Integer id);
     @Update("UPDATE " + tableName + " SET name=#{name}, description=#{description}, createTime=#{createTime} WHERE id = #{id}")
     Integer update(Role role);


}
