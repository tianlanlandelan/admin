package com.yangkaile.admin.log.service;

import com.yangkaile.admin.common.response.ResultData;
import com.yangkaile.admin.log.entity.EmailLog;
import com.yangkaile.admin.log.mapper.EmailLogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author yangkaile
 * @date 2018-11-22 15:12:53
 */
@Service
public class EmailLogService {
    @Resource
    private EmailLogMapper emailLogMapper;

    public ResultData insert(EmailLog emailLog){
        emailLogMapper.insert(emailLog);
        return ResultData.success(emailLog.getId());
    }

    public ResultData checkVerificationCode(String email,String code){
        EmailLog emailLog = emailLogMapper.getByEmail(email);
        if(emailLog != null && emailLog.getCode() != null && emailLog.getCode().equals(code)){
            emailLogMapper.update(emailLog.getId());
            return ResultData.success();
        }
        return ResultData.error("验证码错误");
    }


    public EmailLog getById(Integer id){

        return emailLogMapper.getById(id);
    }

    public EmailLog getByType(String type){
        return emailLogMapper.getByType(type);
    }

    public EmailLog getByRecipients(String recipients){
        return emailLogMapper.getByRecipients(recipients);
    }




    public List<EmailLog> getAll(){
        return emailLogMapper.getAll();
    }


    public List<EmailLog> getListByCondition(Map params){
        return emailLogMapper.getListByCondition(params);
    }

}
