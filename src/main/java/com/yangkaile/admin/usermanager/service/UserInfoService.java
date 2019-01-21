package com.yangkaile.admin.usermanager.service;

import com.yangkaile.admin.common.mybatis.MyBaseEntity;
import com.yangkaile.admin.common.mybatis.MyBaseUtils;
import com.yangkaile.admin.common.response.ResultData;
import com.yangkaile.admin.usermanager.entity.UserInfo;
import com.yangkaile.admin.usermanager.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yangkaile
 * @date 2018-11-16 21:00:34
 */
@Service
public class UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;

    MyBaseEntity baseEntity = MyBaseUtils.getBaseEntity(UserInfo.class);

    public ResultData getById(Integer id){
        if(id == null){
            return ResultData.error("用户ID为空");
        }

        baseEntity.setId(id);
        return ResultData.success(userInfoMapper.baseGetById(baseEntity));
    }

    public ResultData getAll(){
        return ResultData.success(userInfoMapper.baseGetAll(baseEntity));
    }

    public ResultData getPageList(int currentPage,int pageSize){
        return MyBaseUtils.getPageList(currentPage,pageSize,userInfoMapper,baseEntity);
    }

    public Integer deleteById(Integer id){
        return userInfoMapper.deleteById(id);
    }

    public Integer update(UserInfo userInfo){
        return userInfoMapper.update(userInfo);
    }


}
