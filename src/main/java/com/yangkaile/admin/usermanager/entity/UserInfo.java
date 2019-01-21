package com.yangkaile.admin.usermanager.entity;

import com.yangkaile.admin.common.mybatis.TableAttribute;

import java.util.Date;

/**
 * @author yangkaile
 * @date 2018-11-29 19:57:04
 */
@TableAttribute("user_info")
public class UserInfo {
    /**
    * id
    */
     private Integer id;
    /**
    * 用户昵称
    */
     private String nickName;
    /**
    * 生日
    */
     private Date birthday;
    /**
    * 性别（男:0，女:1）
    */
     private Integer gender = 0;
    /**
    * 地址
    */
     private String address;
    /**
    * 签名
    */
     private String signature;
    /**
    * 用户头像
    */
     private String userPortrait;
    /**
    * 邮箱
    */
     private String email;
    /**
    * 手机号
    */
     private String phone;
    /**
    * 创建时间
    */
     private Date createTime = new Date();
    /**
    * 掩码值，用来表示一系列开关
    */
     private Long mask;

     public UserInfo(){

     }

     public UserInfo(Integer id,String phone,String email){
         this.id = id;
         this.phone = phone;
         this.email = email;
     }
     public Integer getId(){
           return this.id;
     }
     public void setId(Integer id){
           this.id = id;
     }
     public String getNickName(){
           return this.nickName;
     }
     public void setNickName(String nickName){
           this.nickName = nickName;
     }
     public Date getBirthday(){
           return this.birthday;
     }
     public void setBirthday(Date birthday){
           this.birthday = birthday;
     }
     public Integer getGender(){
           return this.gender;
     }
     public void setGender(Integer gender){
           this.gender = gender;
     }
     public String getAddress(){
           return this.address;
     }
     public void setAddress(String address){
           this.address = address;
     }
     public String getSignature(){
           return this.signature;
     }
     public void setSignature(String signature){
           this.signature = signature;
     }
     public String getUserPortrait(){
           return this.userPortrait;
     }
     public void setUserPortrait(String userPortrait){
           this.userPortrait = userPortrait;
     }
     public String getEmail(){
           return this.email;
     }
     public void setEmail(String email){
           this.email = email;
     }
     public String getPhone(){
           return this.phone;
     }
     public void setPhone(String phone){
           this.phone = phone;
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
     public void setMask(Long mask){
           this.mask = mask;
     }


@Override
    public String toString() {
        return "UserInfo{" +
            "  id:" + id + "  nickName:" + nickName + "  birthday:" + birthday + "  gender:" + gender + "  address:" + address + "  signature:" + signature + "  userPortrait:" + userPortrait + "  email:" + email + "  phone:" + phone + "  createTime:" + createTime + "  mask:" + mask + 
        "}";
    }
  }
