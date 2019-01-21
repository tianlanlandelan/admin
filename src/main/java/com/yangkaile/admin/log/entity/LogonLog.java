package com.yangkaile.admin.log.entity;

import com.yangkaile.admin.common.mybatis.TableAttribute;

import java.util.Date;

/**
 * @author 董晨龙
 * @date 2018-08-18 10:04:12
 */
@TableAttribute("logon_log")
public class LogonLog {
    /**
    * id
    */
     private Integer id;
    /**
    * 用户ID
    */
     private Integer userId;
    /**
    * 登录类型 0:登录 1:登出
    */
     private Integer type;
    /**
    * 登录方式 0:用户名登录 1:手机号登录 2:邮箱登录
    */
     private Integer way;
    /**
    * IP
    */
     private String ip;
    /**
    * 设备ID
    */
     private String deviceId;
    /**
    * 时间
    */
     private Date createTime = new Date();
     public Integer getId(){
           return this.id;
     }
     public void setId(Integer id){
           this.id = id;
     }
     public Integer getUserId(){
           return this.userId;
     }
     public void setUserId(Integer userId){
           this.userId = userId;
     }
     public Integer getType(){
           return this.type;
     }
     public void setType(Integer type){
           this.type = type;
     }
     public Integer getWay(){
           return this.way;
     }
     public void setWay(Integer way){
           this.way = way;
     }
     public String getIp(){
           return this.ip;
     }
     public void setIp(String ip){
           this.ip = ip;
     }
     public String getDeviceId(){
           return this.deviceId;
     }
     public void setDeviceId(String deviceId){
           this.deviceId = deviceId;
     }
     public Date getCreateTime(){
           return this.createTime;
     }
     public void setCreateTime(Date createTime){
           this.createTime = createTime;
     }


@Override
    public String toString() {
        return "LogonLog{" +
            "  id:" + id + "  userId:" + userId + "  type:" + type + "  way:" + way + "  ip:" + ip + "  deviceId:" + deviceId + "  createTime:" + createTime + 
        "}";
    }
  }
