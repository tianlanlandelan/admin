package com.yangkaile.admin.proxy.controller;

import com.yangkaile.admin.common.response.MyResponse;
import com.yangkaile.admin.common.response.MyResponseReader;
import com.yangkaile.admin.common.router.MyLogRouter;
import com.yangkaile.admin.common.router.MyPublicServiceRouter;
import com.yangkaile.admin.common.router.MyRouters;
import com.yangkaile.admin.common.router.MyUserManagerRouter;
import com.yangkaile.admin.common.util.JsonUtils;
import com.yangkaile.admin.common.util.StringUtils;
import com.yangkaile.admin.proxy.cache.CacheUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author yangkaile
 * @date 2018-11-23 14:34:52
 */
@RestController
@RequestMapping("/base")
@CrossOrigin(origins = "*",allowedHeaders="*", maxAge = 3600)
public class BaseController {

    private Logger logger = LoggerFactory.getLogger(BaseController.class);
    @Resource
    RestTemplate restTemplate;

    @Resource
    private HttpServletRequest request;

    /**
     * 统一的登录接口
     * @param json
     * @param userName  用户名、手机号或邮箱
     * @param password  密码
     * @return
     */
    @RequestMapping(value = "/logon",method = RequestMethod.POST)
    public ResponseEntity logon(@RequestBody String json, String userName, String password){
        if(StringUtils.isEmpty(userName,password)){
            userName = JsonUtils.getString(json,"userName");
            password = JsonUtils.getString(json,"password");
        }
        logger.info("userName:" + userName + "，password:" + password);
        try {
            logger.info("logon  userName:" + userName);
            if(StringUtils.isEmpty(userName,password)){
                return MyResponse.badRequest();
            }
            Map<String, String> map = new HashMap<>(16);
            map.put("userName", userName);
            map.put("password",password);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                    MyRouters.getRouterUrl(MyUserManagerRouter.LOGON)
                            + "?userName={userName}&password={password}",null,String.class,map);
            if(!MyResponseReader.isSuccess(responseEntity)){
                return MyResponse.badRequest();
            }
            //登录成功，缓存
            setCacheForLogon(responseEntity);
            return  responseEntity;
        }catch (HttpClientErrorException e){
            e.printStackTrace();
            logger.warn("HttpClientErrorException:" + e.getStatusCode());
            return CacheUtils.getResponseFromException(e);
        }
    }

    /**
     * 注册接口
     * @param json
     * @param userName  用户名
     * @param password  密码
     * @param verificationCode  验证码
     * @return
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody String json, String userName, String password, String verificationCode){
        if(StringUtils.isEmpty(userName,password,verificationCode)){
            userName = JsonUtils.getString(json,"userName");
            password = JsonUtils.getString(json,"password");
            verificationCode = JsonUtils.getString(json,"verificationCode");
        }
        try {
            logger.info("register  :" );
            if(StringUtils.isEmpty(userName,password,verificationCode)){
                return MyResponse.badRequest();
            }
            Map<String, String> map = new HashMap<>();
            map.put("userName",userName);
            map.put("password",password);
            map.put("verificationCode",verificationCode);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                    MyRouters.getRouterUrl(MyUserManagerRouter.REGISTER)
                            + "?userName={userName}&password={password}&verificationCode={verificationCode}",
                    null,String.class,map);
            return  responseEntity;
        }catch (HttpClientErrorException e){
            logger.warn("HttpClientErrorException:" + e.getStatusCode());
            return CacheUtils.getResponseFromException(e);
        }
    }

    /**
     * 发送短信验证码
     * @param phone
     * @return
     */
    @RequestMapping(value = "/sendSMSCode",method = RequestMethod.GET)
    public ResponseEntity sendSMSCode(String phone){
        try {
            if(StringUtils.isEmpty(phone)){
                return MyResponse.badRequest();
            }
            logger.info("sendSMSCode  :" + phone );
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                    MyRouters.getRouterUrl(MyPublicServiceRouter.SEND_VERIFICATION_CODE_SMS) + "?phone=" + phone, String.class);
            return  responseEntity;
        }catch (HttpClientErrorException e){
            logger.warn("HttpClientErrorException:" + e.getStatusCode());
            return CacheUtils.getResponseFromException(e);
        }
    }
    @RequestMapping(value = "/checkSMSCode",method = RequestMethod.GET)
    public ResponseEntity checkSMSCode(String phone, String code){
        try {
            if(StringUtils.isEmpty(phone,code)){
                return MyResponse.badRequest();
            }
            logger.info("checkEmailCode  p:" + phone + ",code:" + code);
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                    MyRouters.getRouterUrl(MyLogRouter.CHECK_SMS_CODE)
                            + "?phone=" + phone + "&code=" + code,String.class);
            return  responseEntity;
        }catch (HttpClientErrorException e){
            logger.warn("HttpClientErrorException:" + e.getStatusCode());
            return CacheUtils.getResponseFromException(e);
        }
    }

    /**
     * 发送邮件验证码
     * @param email
     * @return
     */
    @RequestMapping(value = "/sendEmailCode",method = RequestMethod.GET)
    public ResponseEntity sendEmailCode(String email){
        try {
            if(StringUtils.isEmpty(email)){
                return MyResponse.badRequest();
            }
            logger.info("sendEmailCode  :" + email );
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                    MyRouters.getRouterUrl(MyPublicServiceRouter.SEND_VERIFICATION_CODE_EMAIL) + "?email=" + email,String.class);
            return  responseEntity;
        }catch (HttpClientErrorException e){
            logger.warn("HttpClientErrorException:" + e.getStatusCode());
            return CacheUtils.getResponseFromException(e);
        }
    }

    @RequestMapping(value = "/checkEmailCode",method = RequestMethod.GET)
    public ResponseEntity checkEmailCode(String email, String code){
        try {
            if(StringUtils.isEmpty(email,code)){
                return MyResponse.badRequest();
            }
            logger.info("checkEmailCode  email:" + email + ",code:" + code);
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                    MyRouters.getRouterUrl(MyLogRouter.CHECK_EMAIL_CODE)
                            + "?email=" + email + "&code=" + code,String.class);
            return  responseEntity;
        }catch (HttpClientErrorException e){
            logger.warn("HttpClientErrorException:" + e.getStatusCode());
            return CacheUtils.getResponseFromException(e);
        }
    }



    /**
     * 设置登录时的缓存
     * 包含请求session和用户权限缓存
     * @param response
     */
    private void setCacheForLogon(ResponseEntity<String> response){
        logger.info("setCacheForLogon :" + response);
        if(!MyResponseReader.isSuccess(response)){
            return;
        }
        int userId = MyResponseReader.getDataObject(response,"id",Integer.class);
        //将userId放入Session
        request.getSession().setAttribute("userId",userId);

        //查询用户的权限Id
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                MyRouters.getRouterUrl(MyUserManagerRouter.GET_ROUTER_IDS_BY_USER_ID)
                        + "?" + CacheUtils.USER_ID +"=" + userId,String.class);

        logger.info("USER_MANAGER_PERMISSION_GET_ROUTER_IDS_BY_USER_ID response:" + responseEntity.getBody());
        if(!MyResponseReader.isSuccess(responseEntity)){
            return;
        }
        List<Integer> routerIds = MyResponseReader.getList(responseEntity,Integer.class);
        logger.info("routerIds:" + routerIds);
        //routerIds放入缓存
        CacheUtils.userRouterMap.put(userId,routerIds);
        //用户权限放入缓存
        responseEntity = restTemplate.getForEntity(
                MyRouters.getRouterUrl(MyUserManagerRouter.GET_ROLE_BY_USER_ID)
                        + "?" + CacheUtils.USER_ID +"=" + userId,String.class);
        if(!MyResponseReader.isSuccess(responseEntity)){
            return;
        }
        String roleName = MyResponseReader.getDataObject(responseEntity,"name",String.class);
        //角色名放入缓存
        CacheUtils.userRoleMap.put(userId,roleName);
        logger.info("logonWithUserName roleName :" + roleName + ", routerIds:" + routerIds);
    }
}
