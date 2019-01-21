package com.yangkaile.admin.usermanager.controller;

import com.yangkaile.admin.common.response.MyResponse;
import com.yangkaile.admin.common.response.ResultData;
import com.yangkaile.admin.common.router.MyUserManagerRouter;
import com.yangkaile.admin.common.router.RouterAttribute;
import com.yangkaile.admin.common.util.StringUtils;
import com.yangkaile.admin.usermanager.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 登录控制
 * 负责用户的登录和注册
 * @author 杨凯乐
 * @date 2018-07-30 09:17:03
 */
@RestController
@RequestMapping("logon")
public class LogonController {
    private Logger logger = LoggerFactory.getLogger(LogonController.class);

    @Resource
    private UserService userService;

    /**
     *
     * @param userName
     * @param password
     * @return
     */
    @RouterAttribute(id = MyUserManagerRouter.LOGON, description = "登录接口")
    @RequestMapping(value = "/logon",method = RequestMethod.POST)
    public ResponseEntity logon(String userName,String password){
        logger.info("received request logon:" + userName + "--" + password);
        if(StringUtils.isEmpty(userName,password)){
            return MyResponse.badRequest();
        }else{
            ResultData response = userService.logon(userName,password);
            return MyResponse.ok(response);
        }
    }

    /**
     * 注册接口
     * @param userName  手机号或邮箱
     * @param password  密码  必填
     * @param verificationCode 验证码
     * @return
     */
    @RouterAttribute(id = MyUserManagerRouter.REGISTER, description = "注册接口，所有参数必填。先通过短信或邮件获取验证码，再调用该接口设置用户名和密码")
    @RequestMapping(value = "/register" , method = RequestMethod.POST)
    public ResponseEntity register(String userName,String password,String verificationCode) {
        try {
            if (StringUtils.isEmpty(userName,password,verificationCode)) {
                return MyResponse.badRequest();
            }
            logger.info("user register:" + userName);
            return MyResponse.ok(userService.register(userName,password, verificationCode));
        } catch (Exception e) {
            e.printStackTrace();
            return MyResponse.serverError();
        }
    }

    @RouterAttribute(id = MyUserManagerRouter.CHECK_USER_REGISTERED, description = "注册接口，所有参数必填。先通过短信或邮件获取验证码，再调用该接口设置用户名和密码")
    @RequestMapping(value = "/checkUserRegistered" , method = RequestMethod.POST)
    public ResponseEntity checkUserRegistered(String userName) {
        try {
            if (StringUtils.isEmpty(userName)) {
                return MyResponse.badRequest();
            }
            if(userService.checkUserRegistered(userName) == null){
                return MyResponse.ok(ResultData.success());
            }else {
                return MyResponse.ok(ResultData.error("用户已注册"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return MyResponse.serverError();
        }
    }

}
