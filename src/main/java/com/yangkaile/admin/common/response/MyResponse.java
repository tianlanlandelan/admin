package com.yangkaile.admin.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * @author yangkaile
 * @date 2018-10-22 14:29:07
 * 规定Controller统一的消息返回格式
 */
public class MyResponse {
    /**
     * 返回OK
     * @param object
     * @return
     */
    public static ResponseEntity ok(ResultData object){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(object);
    }

    /**
     * 异常请求
     * @return
     */
    public static ResponseEntity badRequest(){
        ResultData response = ResultData.error("请求参数异常");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    /**
     * 没有登录
     * @return
     */
    public static  ResponseEntity unauthorized(){
        ResultData response = ResultData.error("用户未登录");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    /**
     * 没有访问权限
     * @return
     */
    public static  ResponseEntity forbidden(){
        ResultData response = ResultData.error("没有权限");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    /**
     * 系统内部错误
     * @return
     */
    public static  ResponseEntity serverError(){
        ResultData response = ResultData.error("系统内部错误");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    public static ResponseEntity u(String message){
        ResultData response = ResultData.error(message);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON).body(response);
    }
}
