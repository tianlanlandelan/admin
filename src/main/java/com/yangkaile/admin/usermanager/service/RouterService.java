package com.yangkaile.admin.usermanager.service;

import com.yangkaile.admin.common.mybatis.MyBaseEntity;
import com.yangkaile.admin.common.mybatis.MyBaseUtils;
import com.yangkaile.admin.common.response.ResultData;
import com.yangkaile.admin.usermanager.entity.RoleRouters;
import com.yangkaile.admin.usermanager.entity.Router;
import com.yangkaile.admin.usermanager.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangkaile
 * @date 2018-09-28 15:04:58
 */
@Service
public class RouterService {
    @Resource
    private RouterMapper routerMapper;

    @Resource
    private RoleRoutersMapper roleRoutersMapper;

    MyBaseEntity baseEntity =  MyBaseUtils.getBaseEntity(Router.class);


    private Logger logger = LoggerFactory.getLogger(RouterService.class);


    public ResultData getAll(){
//        baseEntity.setCustomizedFields("requestType","controllerName","routerUrl");
//        return ResultData.success(routerMapper.baseGetAllCustomizedFields(baseEntity));
        return ResultData.success(routerMapper.baseGetAll(baseEntity));
    }
    public ResultData getRoutersByRoleId(int roleId){
        return ResultData.success(routerMapper.getRoutersByRoleId(roleId));
    }

    public ResultData addRouterForRole(RoleRouters roleRouters){
        return ResultData.success(roleRoutersMapper.insert(roleRouters));
    }

    public ResultData getRouterIdsByUserId(Integer userId){
        List<RoleRouters> list = roleRoutersMapper.getByUserId(userId);
        List<Integer> result = new ArrayList<>();
        for(RoleRouters roleRouters : list){
            result.add(roleRouters.getRouterId());
        }
        return ResultData.success(result);
    }

    /**
     * 分页查询
     * 只有在查询第一页数据时会返回记录总条数
     * @param currentPage   当前页码 不能小于0
     * @param pageSize      每页数据条数 不能小于0 不能超过100
     * @return
     */
    public ResultData getPageList(int currentPage,int pageSize){
        return MyBaseUtils.getPageList(currentPage,pageSize,routerMapper,baseEntity);
    }


}
