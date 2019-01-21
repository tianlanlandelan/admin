package com.yangkaile.admin.log.service;

import com.yangkaile.admin.common.response.ResultData;
import com.yangkaile.admin.log.entity.SMSLog;
import com.yangkaile.admin.log.mapper.SMSLogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yangkaile
 * @date 2018-08-30 09:25:14
 */
@Service
public class SMSLogService {
    @Resource
    private SMSLogMapper smsLogMapper;

    public ResultData insert(SMSLog smsLog){
        smsLogMapper.insert(smsLog);
        return ResultData.success(smsLog.getId());
    }

    /**
     * 验证短信验证码并修改状态
     * 当验证码校验成功后，修改验证码状态为已使用
     * 当验证码校验失败后，不修改验证码状态
     * @param phone
     * @param codeStr
     * @return
     */
    public ResultData checkAndUpdateState(String phone,String codeStr){
        SMSLog SMSLog = smsLogMapper.getByPhone(phone);
        if(SMSLog != null && SMSLog.getCodeStr() != null && SMSLog.getCodeStr().equals(codeStr)){
            smsLogMapper.update(SMSLog.getId());
            return ResultData.success();
        }
        return ResultData.error("验证码错误");
    }


    public ResultData getByPhone(String phone){
        return ResultData.success(smsLogMapper.getByPhone(phone));
    }


    public SMSLog getById(Integer id){

        return smsLogMapper.getById(id);
    }



    public SMSLog getByType(Integer type){
        return smsLogMapper.getByType(type);
    }


    public List<SMSLog> getAll(){
        return smsLogMapper.getAll();
    }

}
