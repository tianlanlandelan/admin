package com.yangkaile.admin.usermanager.service;

import com.yangkaile.admin.common.encryption.MyMD5Utils;
import com.yangkaile.admin.common.response.MyResponseReader;
import com.yangkaile.admin.common.response.ResultData;
import com.yangkaile.admin.common.router.MyLogRouter;
import com.yangkaile.admin.common.router.MyRouters;
import com.yangkaile.admin.common.util.StringUtils;
import com.yangkaile.admin.common.util.ValidUserName;
import com.yangkaile.admin.usermanager.config.MyConfig;
import com.yangkaile.admin.usermanager.entity.User;
import com.yangkaile.admin.usermanager.entity.UserInfo;
import com.yangkaile.admin.usermanager.entity.UserRoles;
import com.yangkaile.admin.usermanager.mapper.UserInfoMapper;
import com.yangkaile.admin.usermanager.mapper.UserMapper;
import com.yangkaile.admin.usermanager.mapper.UserRolesMapper;
import com.yangkaile.admin.usermanager.utils.LogonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangkaile
 * @date 2018-08-19 12:02:38
 */
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserRolesMapper userRolesMapper;

    @Resource
    RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * 登录
     * 判断是否使用密码登录
     *  如果使用密码登录，看是用户名密码、手机号密码、邮箱密码的哪种组合
     *      如果密码校验无误，返回用户ID
     *      如果密码校验失败或不支持指定的密码登录组合，则返回相应的错误信息
     *  如果不是密码登录，看是否是微信登录
     * @param userName
     * @param password
     * @return
     */
    public ResultData logon(String userName,String password)  {
        User user = checkPassword(userName,password);
        if(user == null){
            return ResultData.error("非法访问");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId",user.getId());
        map.put("type",LogonUtils.LOGON_TYPE_LOGON);
        map.put("way", logonWay(userName,user));
        map.put("ip","ddd");
        logger.info("logCenterUrl ：" + MyRouters.getRouterUrl(MyLogRouter.INSERT_LOGON_LOG));
        //记录登录日志
        restTemplate.postForEntity(
                MyRouters.getRouterUrl(MyLogRouter.INSERT_LOGON_LOG) +
                        "?userId={userId}&type={type}&way={way}&ip={ip}",null,String.class,map);
        logger.info("logonLog Ok: Id:"  + user.getId() + ",userName:" + userName);
        user.setPassword("");
        user.setMask(0);
        return ResultData.success(user);
    }
    /**
     *  注册
     *  校验用户名是否已注册，校验验证码是否正确
     *  校验通过后注册用户，并为其添加默认角色
     * @param userName
     * @param password
     * @param verificationCode
     * @return
     * @throws Exception
     */
    public ResultData register (String userName,String password,String verificationCode) throws Exception{
        User user = checkUserRegistered(userName);
        if(user != null){
            return ResultData.error("用户已存在");
        }
        user = new User();
        if (ValidUserName.isValidPhoneNumber(userName)) {
            user.setPhone(userName);
            user.setPhoneLogon();
            //去logCenter核对短信验证码发送记录
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                    MyRouters.getRouterUrl(MyLogRouter.CHECK_SMS_CODE)
                            + "?phone=" + userName + "&code=" + verificationCode,String.class);
            if(!MyResponseReader.isSuccess(responseEntity)){
                return ResultData.error("验证码错误");
            }
        } else if (ValidUserName.isValidEmailAddress(userName)) {
            user.setEmail(userName);
            user.setEmailLogon();
            //去logCenter核对邮件发送记录
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                    MyRouters.getRouterUrl(MyLogRouter.CHECK_EMAIL_CODE)
                            + "?email=" + userName + "&code=" + verificationCode,String.class);
            if(!MyResponseReader.isSuccess(responseEntity)){
                return ResultData.error("验证码错误");
            }
        } else {
            return ResultData.error("仅支持手机号和邮箱注册");
        }
        user.setPassword(MyMD5Utils.EncoderByMd5(password));
        userMapper.insert(user);
        UserInfo userInfo = new UserInfo(user.getId(),user.getPhone(),user.getEmail());
        userInfoMapper.insert(userInfo);
        UserRoles userRoles = new UserRoles(user.getId(),MyConfig.DEFAULT_ROLE_ID);
        userRolesMapper.insert(userRoles);
        return ResultData.success(user.getId());
    }

    /**
     * 检查登录方式
     * @param userName
     * @param user
     * @return
     */
    private int logonWay(String userName,User user){
        if(ValidUserName.isValidPhoneNumber(userName) && user.isPermitPhoneLogon()){
            return LogonUtils.LOGON_WAY_PHONE;
        }
        if(ValidUserName.isValidEmailAddress(userName) && user.isPermitUserNameLogon()){
            return LogonUtils.LOGON_WAY_EMAIL;
        }
        if(user.isPermitUserNameLogon()){
            return LogonUtils.LOGON_WAY_USERNAME;
        }
        return -1;
    }

    /**
     * 校验用户名密码，同时返回该用户数据
     * @param userName
     * @param password
     * @return
     */
    private User checkPassword(String userName,String password){
        User user = checkUserRegistered(userName);
        if(user == null){
            return null;
        }
        try{
            if(MyMD5Utils.checkqual(password,user.getPassword()) && logonWay(userName,user) != -1){
                return user;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * 检验用户名、手机号、邮箱是否注册过
     * @param userName 用户名、手机号、邮箱
     * @return
     */
    public User checkUserRegistered(String userName){
        if(StringUtils.isEmpty(userName)){
            return null;
        }
        if(ValidUserName.isValidPhoneNumber(userName)){
            return userMapper.getByPhone(userName);
        }else if(ValidUserName.isValidEmailAddress(userName)){
            return userMapper.getByEmail(userName);
        }else{
            return userMapper.getByUserName(userName);
        }
    }

    /**
     * 根据角色查找用户
     * @param roleId
     * @return
     */
    public ResultData getUsersByRoleId(int roleId){
        return ResultData.success(userMapper.getUsersByRoleId(roleId));
    }

    public ResultData getAllUserNameAndRoleName(){
        return ResultData.success(userMapper.getAllUserNameAndRoleName());
    }

    public Integer update(User user){
        return userMapper.update(user);
    }


}
