package com.yangkaile.admin.usermanager.controller;

import com.yangkaile.admin.common.response.MyResponse;
import com.yangkaile.admin.common.router.MyUserManagerRouter;
import com.yangkaile.admin.common.router.RouterAttribute;
import com.yangkaile.admin.usermanager.entity.Role;
import com.yangkaile.admin.usermanager.entity.UserRoles;
import com.yangkaile.admin.usermanager.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *  角色管理
 * @author 杨凯乐
 * @date 2018-07-30 09:18:05
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    private Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Resource
    private RoleService roleService;


    /**
     * 查询所有角色
     * @return
     */
    @RouterAttribute(id = MyUserManagerRouter.GET_ALL_ROLES, description = "获取所有角色")
    @RequestMapping(value = "/getAllRoles" , method = RequestMethod.GET)
    public ResponseEntity getAllRoles(){
        return MyResponse.ok(roleService.getAll());
    }

    /**
     * 查询用户的角色
     * @param userId
     * @return
     */
    @RouterAttribute(id = MyUserManagerRouter.GET_ROLE_BY_USER_ID, description = "获取用户角色")
    @RequestMapping(value = "/getRoleByUserId" , method = RequestMethod.GET)
    public ResponseEntity getRoleByUserId(Integer userId){
        if(userId == null || userId < 0){
            return MyResponse.badRequest();
        }
        return MyResponse.ok(roleService.getRoleByUserId(userId));
    }

    /**
     * 查询包含某个权限的角色
     * @param routerId
     * @return
     */
    @RouterAttribute(id = MyUserManagerRouter.GET_ROLES_BY_ROUTER_ID, description = "查询拥有某权限的角色")
    @RequestMapping(value = "/getRolesByRouterId" , method = RequestMethod.GET)
    public ResponseEntity getRolesByRouterId(Integer routerId){
        if(routerId == null || routerId < 0){
            return MyResponse.badRequest();
        }
        return MyResponse.ok(roleService.getRolesByRouterId(routerId));
    }

    /**
     * 添加角色
     * @param name
     * @param description
     * @return
     */
    @RouterAttribute(id = MyUserManagerRouter.ADD_ROLE, description = "我是登录接口")
    @RequestMapping(value = "/addRole" , method = RequestMethod.POST)
    public ResponseEntity addRole(String name,String description){
        if(name == null || description == null){
            return MyResponse.badRequest();
        }
        Role role = new Role(name,description);
        return MyResponse.ok(roleService.insert(role));
    }

    /**
     * 为用户添加角色
     * @param userId
     * @param roleId
     * @return
     */
    @RouterAttribute(id = MyUserManagerRouter.ADD_ROLE_FOR_USER, description = "我是登录接口")
    @RequestMapping(value = "/addRoleForUser" , method = RequestMethod.POST)
    public ResponseEntity addRoleForUser(Integer userId,Integer roleId){
        if(userId == null || roleId == null){
            return MyResponse.badRequest();
        }
        UserRoles userRoles = new UserRoles(userId, roleId);
        return MyResponse.ok(roleService.addRoleForUser(userRoles));
    }
    /**
     * 删除角色
     * 这个接口要慎重调用
     * 删除角色的同时，会删除所有用户和权限中与该角色相关的记录
     * @param id    角色id
     * @return
     */
    @RouterAttribute(id = MyUserManagerRouter.DELETE_ROLE_BY_ID, description = "我是登录接口")
    @RequestMapping(value = "/deleteRoleById" ,method = RequestMethod.DELETE)
    public ResponseEntity deleteRoleById(Integer id){
        if(id == null){
            return MyResponse.badRequest();
        }
        return MyResponse.ok(roleService.deleteById(id));
    }

    /**
     * 修改角色
     * @param id    角色Id notNull
     * @param name  新的角色名 notNull
     * @param description 新的角色描述 null
     * @return
     */
    @RouterAttribute(id = MyUserManagerRouter.UPDATE_ROLE, description = "我是登录接口")
    @RequestMapping(value = "/updateRole" ,method = RequestMethod.PUT)
    public ResponseEntity updateRole(Integer id,String name,String description){
        if(id == null || name == null){
            return MyResponse.badRequest();
        }
        Role role = new Role(id,name);
        if(description != null){
            role.setDescription(description);
        }
        return MyResponse.ok(roleService.update(role));
    }
}
