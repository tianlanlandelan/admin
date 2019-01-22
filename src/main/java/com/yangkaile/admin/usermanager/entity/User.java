package com.yangkaile.admin.usermanager.entity;

import com.yangkaile.admin.common.mybatis.FieldAttribute;
import com.yangkaile.admin.common.mybatis.TableAttribute;

import java.util.Date;

/**
 * @author yangkaile
 * @date 2018-11-29 19:54:56
 */
@TableAttribute("usermanager_user")
public class User {
    /**
     * 允许使用用户名登录
     */
    private static int permitUserNameLogon = 1 << 0;
    /**
     * 允许使用手机号登录
     */
    private static int permitPhoneLogon = 1 << 1;
    /**
     * 允许使用邮箱登录
     */
    private static int permitEmailLogon = 1 << 2;
    /**
     * 允许使用微信登录
     */
    private static int permitWXIdLogon = 1 << 3;
    /**
    * id
    */
    @FieldAttribute
     private Integer id;
    /**
    * 用户名,不重复，可用于登录
    */
    @FieldAttribute
     private String userName;
    /**
    * 手机号，不重复，可用于登录
    */
    @FieldAttribute
     private String phone;
    /**
    * 微信号，不重复，可用于登录
    */
    @FieldAttribute
     private String wxId;
    /**
    * 邮箱，不重复，可用于登录
    */
    @FieldAttribute
     private String email;
    /**
    * 密码
    */
    @FieldAttribute
     private String password;
    /**
    * 创建时间
    */
    @FieldAttribute
     private Date createTime = new Date();
    /**
    * 掩码值，用来表示一系列开关（如：是否开启用户名登录、是否已删除、是否开启邮箱登录等）
    */
    @FieldAttribute
     private long mask = 0L;
    /**
     * 是否已删除 0:未删除 1:已删除
     */
    @FieldAttribute
     private int isDelete = 0;
     public Integer getId(){
           return this.id;
     }
     public void setId(Integer id){
           this.id = id;
     }
     public String getUserName(){
           return this.userName;
     }
     public void setUserName(String userName){
           this.userName = userName;
     }
     public String getPhone(){
           return this.phone;
     }
     public void setPhone(String phone){
           this.phone = phone;
     }
     public String getWxId(){
           return this.wxId;
     }
     public void setWxId(String wxId){
           this.wxId = wxId;
     }
     public String getEmail(){
           return this.email;
     }
     public void setEmail(String email){
           this.email = email;
     }
     public String getPassword(){
           return this.password;
     }
     public void setPassword(String password){
           this.password = password;
     }
     public Date getCreateTime(){
           return this.createTime;
     }
     public void setCreateTime(Date createTime){
           this.createTime = createTime;
     }
     public Long getMask(){
           return this.mask;
     }
     public void setMask(long mask){
           this.mask = mask;
     }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public void setUserNameLogon(){
         this.mask = this.mask | permitUserNameLogon;
    }
    public void setPhoneLogon(){
         this.mask = this.mask | permitPhoneLogon;
    }
    public void setEmailLogon(){
        this.mask = this.mask | permitEmailLogon;
    }
    public boolean isPermitUserNameLogon(){
        return (mask & permitUserNameLogon ) == permitUserNameLogon;
     }
     public boolean isPermitPhoneLogon(){
         return (mask & permitPhoneLogon ) == permitPhoneLogon;
     }
     public boolean isPermitEmailLogon(){
         return (mask & permitEmailLogon ) == permitEmailLogon;
     }
     public boolean isPermitWXIdLogon(){
         return (mask & permitWXIdLogon ) == permitWXIdLogon;
     }


@Override
    public String toString() {
        return "User{" +
            "  id:" + id + "  userName:" + userName + "  phone:" + phone + "  wxId:" + wxId + "  email:" + email + "  password:" + password + "  createTime:" + createTime + "  mask:" + mask + 
        "}";
    }
  }
