package com.yangkaile.admin.log.entity;

import java.util.Date;

public class SMSLog {
    /**
    * id
    */
     private Integer id;
    /**
    * 用户手机号
    */
     private String phone;
    /**
    * 类型
    */
     private Integer type;
    /**
    * 短信内容
    */
     private String message;
    /**
     * 验证码
     */
     private String codeStr;

    /**
     *  返回结果
     *  {data={templateSMS={dateCreated=20180827170721, smsMessageSid=a21809d2dbe84872878a3e9cd9a3da17}}, statusCode=000000}
     */
    private String result;
    /**
    * 返回码
    */
     private String statusCode;
    /**
     * 状态 0 正常  1 已使用
     */
    private int state = 0;

    private Date createTime = new Date();

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCodeStr() {
        return codeStr;
    }

    public void setCodeStr(String codeStr) {
        this.codeStr = codeStr;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "SMSLog{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", type=" + type +
                ", message='" + message + '\'' +
                ", codeStr='" + codeStr + '\'' +
                ", result='" + result + '\'' +
                ", statusCode='" + statusCode + '\'' +
                ", state=" + state +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
