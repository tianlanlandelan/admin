package com.yangkaile.admin.externalservice;


/**
 * 暂时放一些静态变量配置
 * @author yangkaile
 * @date 2018-08-28 15:15:57
 */
public class ExternalServiceConfig {
    /**
     * 注册时的短信验证码发送类型
     */
    public static final int SMS_SEND_TYPE_REGISTER = 0;
    /**
     * 注册时的短信模板ID
     */
    public static final String SMS_SEND_TEMPLATE_ID_REGISTER = "1";
    /**
     * 注册时的短信有效期
     */
    public static final String SMS_SEND_MINUTE_REGISTER = "5";

    public static final int SMS_SEND_LENGTH = 6;

    public static final int EMAIL_SEND_TYPE_REGISTER = 0;

    public static final String EMAIL_VERIFICATIONCODE_TITLE = "请查收您的验证码";

    public static final String EMAIL_VERIFICATIONCODE_BODY = "您的验证码是:%1$s";


    public static final int EMAIL_VERIFICATIONCODE_LENGTH = 6;

}
