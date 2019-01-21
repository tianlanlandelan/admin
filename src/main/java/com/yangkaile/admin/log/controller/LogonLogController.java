package com.yangkaile.admin.log.controller;

import com.yangkaile.admin.common.response.MyResponse;
import com.yangkaile.admin.common.router.MyLogRouter;
import com.yangkaile.admin.common.router.RouterAttribute;
import com.yangkaile.admin.log.entity.LogonLog;
import com.yangkaile.admin.log.service.LogonLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 董晨龙
 * @date 2018-08-15 10:05:40
 */
@RestController
@RequestMapping("/logonLog")
public class LogonLogController {
    private Logger logger = LoggerFactory.getLogger(LogonLogController.class);

    @Resource
    private LogonLogService logonLogService;

    @RouterAttribute(id = MyLogRouter.INSERT_LOGON_LOG, description = "添加登录日志")
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public ResponseEntity insert(LogonLog logonLog){
        return MyResponse.ok(logonLogService.insert(logonLog));
    }


    @RouterAttribute(id = MyLogRouter.GET_ALL_LOGON_LOG ,description = "查询所有登录日志")
    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    public ResponseEntity getAll(){
        return ResponseEntity.ok(logonLogService.getAll());
    }

    @RouterAttribute(id = MyLogRouter.GET_LOGON_LOG_BY_PAGE ,description = "分页查询登录日志")
    @RequestMapping(value = "/getPageList",method = RequestMethod.GET)
    public ResponseEntity getPageList(Integer currentPage, Integer pageSize){
        if(currentPage == null || pageSize == null){
            return MyResponse.badRequest();
        }
        return ResponseEntity.ok(logonLogService.getPageList(currentPage,pageSize));
    }




    @RequestMapping(value = "/getByUserId",method = RequestMethod.GET)
    public ResponseEntity getByUserId(Integer userId){
        LogonLog result = logonLogService.getByUserId(userId);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(result);
    }

    @RequestMapping(value = "/getByType",method = RequestMethod.GET)
    public ResponseEntity getByType(Integer type){
        LogonLog result = logonLogService.getByType(type);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(result);
    }






}
