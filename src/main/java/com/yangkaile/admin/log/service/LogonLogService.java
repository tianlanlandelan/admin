package com.yangkaile.admin.log.service;


import com.yangkaile.admin.common.mybatis.MyBaseEntity;
import com.yangkaile.admin.common.mybatis.MyBaseUtils;
import com.yangkaile.admin.common.response.ResultData;
import com.yangkaile.admin.log.entity.LogonLog;
import com.yangkaile.admin.log.mapper.LogonLogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yangkaile
 * @date 2018-11-22 15:14:14
 */
@Service
public class LogonLogService {
    @Resource
    private LogonLogMapper logonLogMapper;

    MyBaseEntity myBaseEntity = MyBaseUtils.getBaseEntity(LogonLog.class);

    public ResultData insert(LogonLog logonLog){
        return ResultData.success(logonLogMapper.insert(logonLog));
    }


    public ResultData getAll(){
        return ResultData.success(logonLogMapper.baseGetAll(myBaseEntity));
    }

    public ResultData getPageList(int currentPage,int pageSize){
        return MyBaseUtils.getPageList(currentPage,pageSize,logonLogMapper,myBaseEntity);
    }


    public LogonLog getByUserId(Integer userId){
        return logonLogMapper.getByUserId(userId);
    }

    public LogonLog getByType(Integer type){
        return logonLogMapper.getByType(type);
    }
}
