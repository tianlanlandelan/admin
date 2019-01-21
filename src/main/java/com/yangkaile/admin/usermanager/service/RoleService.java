package com.yangkaile.admin.usermanager.service;

import com.yangkaile.admin.common.mybatis.MyBaseEntity;
import com.yangkaile.admin.common.mybatis.MyBaseUtils;
import com.yangkaile.admin.common.response.ResultData;
import com.yangkaile.admin.usermanager.entity.Role;
import com.yangkaile.admin.usermanager.entity.UserRoles;
import com.yangkaile.admin.usermanager.mapper.RoleMapper;
import com.yangkaile.admin.usermanager.mapper.RoleRoutersMapper;
import com.yangkaile.admin.usermanager.mapper.UserRolesMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yangkaile
 * @date 2018-09-05 08:54:10
 */
@Service
public class RoleService {
    @Resource
    private RoleMapper roleMapper;

    MyBaseEntity baseEntity =  MyBaseUtils.getBaseEntity(Role.class);

    @Resource
    private UserRolesMapper userRolesMapper;

    @Resource
    private RoleRoutersMapper roleRoutersMapper;

    public ResultData getAll(){
        return ResultData.success(roleMapper.baseGetAll(baseEntity));
    }

    public ResultData getRoleByUserId(int userId){
        return ResultData.success(roleMapper.getRoleByUserId(userId));
    }

    public ResultData getRolesByRouterId(int userId){
        return ResultData.success(roleMapper.getRolesByRouterId(userId));
    }

    public ResultData addRoleForUser(UserRoles userRoles){
        UserRoles result = userRolesMapper.getByUserId(userRoles.getUserId());
        if(result == null){
            return ResultData.success(userRolesMapper.insert(userRoles));
        }
        return ResultData.success(userRolesMapper.update(userRoles));
    }

    public ResultData deleteById(Integer id){
        userRolesMapper.deleteByRoleId(id);
        roleRoutersMapper.deleteByroleId(id);
        roleMapper.deleteById(id);
        return ResultData.success("已删除");
    }

    public ResultData insert(Role role){
        Role checker = roleMapper.getByName(role);
        if(checker != null){
            return ResultData.error("角色名已存在");
        }
        roleMapper.insert(role);
        return ResultData.success(role.getId());
    }


    public ResultData update(Role role){
        return ResultData.success(roleMapper.update(role));
    }


}
