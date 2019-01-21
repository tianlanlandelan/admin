package com.yangkaile.admin.usermanager.mapper;


import com.yangkaile.admin.common.mybatis.MyBaseMapper;
import com.yangkaile.admin.common.router.MyRouter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * RouterMapper
 * @author yangkaile
 * @date 2018-11-29 14:57:27
 */
@Mapper
public interface RouterMapper extends MyBaseMapper {
    String tableName = "usermanager_router";
    String roleRouters = "usermanager_role_routers";

     @Select({"SELECT a.id,a.name, a.serviceName, a.controllerName, a.methodName, a.routerUrl,a.requestType "
             + " FROM " + tableName + " a ," + roleRouters + " b "
             + " WHERE a.id = b.routerId AND b.roleId = #{roleId}"
     })
     List<MyRouter> getRoutersByRoleId(Integer roleId);
}
