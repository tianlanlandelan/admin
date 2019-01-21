package com.yangkaile.admin.log.entity;

import com.yangkaile.admin.common.util.ConfigUtils;

import java.util.Date;

public class EmailLog {
    /**
    * id
    */
     private Integer id;
    /**
    * 邮件发送类型
    */
     private Integer type;
    /**
    * 收件人
    */
     private String email;
    /**
    * 标题
    */
     private String title;
    /**
    * 内容
    */
     private String content;
    /**
     * 验证码
     * 包含验证码的邮件，需要将验证码单独填写，方便查询
    */
     private String code;


     private String result;
    /**
     * 状态码
     * 0 成功
     * 1 失败
     */
     private int statusCode = ConfigUtils.EMAIL_SEND_STATUSCODE_SUCCESS;

    /**
     * 状态码
     * 0 未用过
     * 1 已用过
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "EmailLog{" +
                "id=" + id +
                ", type=" + type +
                ", email='" + email + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", code='" + code + '\'' +
                ", result='" + result + '\'' +
                ", statusCode=" + statusCode +
                ", state=" + state +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
