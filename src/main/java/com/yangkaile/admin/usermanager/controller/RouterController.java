package com.yangkaile.admin.usermanager.controller;

import com.yangkaile.admin.common.response.MyResponse;
import com.yangkaile.admin.common.router.MyUserManagerRouter;
import com.yangkaile.admin.common.router.RouterAttribute;
import com.yangkaile.admin.usermanager.entity.RoleRouters;
import com.yangkaile.admin.usermanager.service.RouterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *  权限管理
 * @author 杨凯乐
 * @date 2018-07-30 09:18:05
 */
@RestController
@RequestMapping("/router")
public class RouterController {
    private Logger logger = LoggerFactory.getLogger(RouterController.class);

    @Resource
    private RouterService routerService;

    /**
     * 查询所有接口（权限）
     * @return
     */
    @RouterAttribute(id = MyUserManagerRouter.GET_ALL_ROUTERS, description = "获取所有权限")
    @RequestMapping(value = "/getAllRouters" , method = RequestMethod.GET)
    public ResponseEntity getAllRouters(){
        return MyResponse.ok(routerService.getAll());
    }


    /**
     * 分页查询权限
     * @param currentPage 当前页码
     * @param pageSize  每页记录数
     * @return
     */
    @RouterAttribute(id = MyUserManagerRouter.GET_ROUTERS_BY_PAGE, description = "分页查询权限")
    @RequestMapping(value = "/getPageList" , method = RequestMethod.GET)
    public ResponseEntity getPageList(Integer currentPage,Integer pageSize){
        if(currentPage == null || pageSize == null) {
            return MyResponse.badRequest();
        }
        return MyResponse.ok(routerService.getPageList(currentPage,pageSize));
    }

    /**
     * 查询某个角色的所有权限
     * @param roleId
     * @return
     */
    @RouterAttribute(id = MyUserManagerRouter.GET_ROUTERS_BY_ROLE_ID, description = "查询一个角色拥有的权限")
    @RequestMapping(value = "/getRoutersByRoleId" , method = RequestMethod.GET)
    public ResponseEntity getRoutersByRoleId(Integer roleId){
        if(roleId == null || roleId < 0){
            return MyResponse.badRequest();
        }
        return MyResponse.ok(routerService.getRoutersByRoleId(roleId));
    }

    /**
     * 查询某个用户的所有权限
     * @param userId
     * @return
     */
    @RouterAttribute(id = MyUserManagerRouter.GET_ROUTER_IDS_BY_USER_ID, description = "查询一个用户拥有的权限")
    @RequestMapping(value = "/getRouterIdsByUserId" , method = RequestMethod.GET)
    public ResponseEntity getRoutersByUserId(Integer userId){
        if(userId == null){
            return MyResponse.badRequest();
        }
        return MyResponse.ok(routerService.getRouterIdsByUserId(userId));
    }


    /**
     * 为角色添加权限
     * @param roleId
     * @param routerId
     * @return
     */
    @RouterAttribute(id = MyUserManagerRouter.ADD_ROUTER_FOR_ROLE, description = "我是登录接口")
    @RequestMapping(value = "/addRouterForRole" , method = RequestMethod.POST)
    public ResponseEntity addRouterForRole(Integer roleId,Integer routerId){
        if(roleId == null || routerId == null){
            return MyResponse.badRequest();
        }
        RoleRouters roleRouters = new RoleRouters(roleId,routerId);
        return MyResponse.ok(routerService.addRouterForRole(roleRouters));
    }
}
