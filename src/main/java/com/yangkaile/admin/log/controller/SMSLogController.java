package com.yangkaile.admin.log.controller;

import com.yangkaile.admin.common.response.MyResponse;
import com.yangkaile.admin.common.router.MyLogRouter;
import com.yangkaile.admin.common.router.RouterAttribute;
import com.yangkaile.admin.common.util.StringUtils;
import com.yangkaile.admin.common.util.ValidUserName;
import com.yangkaile.admin.log.entity.SMSLog;
import com.yangkaile.admin.log.service.SMSLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yangkaile
 * @date 2018-08-28 21:23:52
 */
@RestController
@RequestMapping("/smsLog")
public class SMSLogController {
    private Logger logger = LoggerFactory.getLogger(SMSLogController.class);

    @Resource
    private SMSLogService smsLogService;

    /**
     * 添加短信验证码发送记录
     * 在PublicServiceCenter组件向用户发送短信验证码后调用该接口记录日志
     * @param phone
     * @param type
     * @param message
     * @param codeStr
     * @param result
     * @param statusCode
     * @return
     */
    @RouterAttribute(id = MyLogRouter.INSERT_SMS_SEND_LOG, description = "添加短信发送记录")
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    ResponseEntity insert(String phone, Integer type ,String message, String codeStr ,String result, String statusCode){
        SMSLog smsLog = new SMSLog();
        smsLog.setPhone(phone);
        smsLog.setType(type);
        smsLog.setMessage(message);
        smsLog.setCodeStr(codeStr);
        smsLog.setResult(result);
        smsLog.setStatusCode(statusCode);
        return MyResponse.ok(smsLogService.insert(smsLog));
    }

    /**
     * 验证短信验证码是否正确
     * @param phone 手机号
     * @param code 验证码
     * @return
     */
    @RouterAttribute(id = MyLogRouter.CHECK_SMS_CODE, description = "校验短信验证码：" +
            "校验上送的验证码是否是最新的没有用过的验证码，如果是:将其置为已使用状态;" +
            "如果不是:返回'验证码错误'的提示")
    @RequestMapping(value = "/checkVerificationCode",method = RequestMethod.GET)
    ResponseEntity getByPhone(String phone, String code){
        if(StringUtils.isEmpty(phone,code) || !ValidUserName.isValidPhoneNumber(phone)){
            return MyResponse.badRequest();
        }
        return MyResponse.ok(smsLogService.checkAndUpdateState(phone,code));
    }



    @RequestMapping(value = "/getById",method = RequestMethod.GET)
    ResponseEntity getById(Integer id){
        SMSLog result = smsLogService.getById(id);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(result);
    }


    @RequestMapping(value = "/getByType",method = RequestMethod.GET)
    ResponseEntity getByType(Integer type){
        SMSLog result = smsLogService.getByType(type);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(result);
    }


    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    ResponseEntity getAll(){
        List<SMSLog> result = smsLogService.getAll();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(result);
    }

}
