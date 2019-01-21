package com.yangkaile.admin.log.controller;

import com.yangkaile.admin.common.response.MyResponse;
import com.yangkaile.admin.common.response.ResultData;
import com.yangkaile.admin.common.router.MyLogRouter;
import com.yangkaile.admin.common.router.RouterAttribute;
import com.yangkaile.admin.common.util.StringUtils;
import com.yangkaile.admin.common.util.ValidUserName;
import com.yangkaile.admin.log.entity.EmailLog;
import com.yangkaile.admin.log.service.EmailLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangkaile
 * @date 2018-11-06 14:00:07
 */
@RestController
@RequestMapping("/emailLog")
public class EmailLogController {
    private Logger logger = LoggerFactory.getLogger(EmailLogController.class);

    @Resource
    private EmailLogService emailLogService;

    @RouterAttribute(id = MyLogRouter.INSERT_EMAIL_SEND_LOG, description = "添加邮件发送记录")
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public ResponseEntity insert(EmailLog entity){
        logger.info("mailSendLog received :" + entity.toString());
        return MyResponse.ok(emailLogService.insert(entity));
    }

    @RouterAttribute(id = MyLogRouter.CHECK_EMAIL_CODE, description = "校验邮件验证码：" +
            "校验上送的验证码是否是最新的没有用过的验证码，如果是:将其置为已使用状态;" +
            "如果不是:返回'验证码错误'的提示")
    @RequestMapping(value = "/checkVerificationCode", method = RequestMethod.GET)
    public ResponseEntity checkVerificationCode(String email, String code){
        if(StringUtils.isEmpty(email,code) || !ValidUserName.isValidEmailAddress(email)){
            return MyResponse.badRequest();
        }
        return MyResponse.ok(emailLogService.checkVerificationCode(email,code));
    }


    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ResponseEntity list(EmailLog log, String startDate, String endDate, Integer page_num, Integer page_size) {
        Map<String, Object> params = new HashMap<>(16);
        ResultData response = new ResultData();
        if (page_num != null || page_size != null) {
            Integer offset = (page_num - 1) * page_size;
            params.put("offset", offset);
            params.put("rows", page_size);
        } else {
            params.put("offset", 0);
            params.put("rows", 10);
        }
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("entity", log);
        response.setData(emailLogService.getListByCondition(params));
        return MyResponse.ok(response);
    }


}
