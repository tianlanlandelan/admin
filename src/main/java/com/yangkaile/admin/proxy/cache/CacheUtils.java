package com.yangkaile.admin.proxy.cache;

import com.yangkaile.admin.common.response.MyResponse;
import com.yangkaile.admin.common.response.ResultData;
import com.yangkaile.admin.common.router.MyRouter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangkaile
 * @date 2018-11-07 09:23:00
 */
public class CacheUtils {

    public final static String USER_ID = "userId";

    public final static String ROLE_MANAGER = "Manager";
    public final static String ROLE_USER = "User";

    private static Map<String,MyRouter> RouterMap = new HashMap<>();
    public static Map<Integer,List<Integer>> userRouterMap = new HashMap<>();
    public static Map<Integer,String> userRoleMap = new HashMap<>();

    private static String lock = "lock";
    public static Map<String,MyRouter> getRouterMap(){
        synchronized (lock){
            return RouterMap;
        }
    }

    public static void initRouterMap(List<MyRouter> list){
        synchronized (lock){
            RouterMap.clear();
            for(MyRouter object:list){
                RouterMap.put(object.getName(),object);
            }
        }
    }

    /**
     * 根据组件返回的错误码重组应答报文
     * @param exception
     * @return
     */
    public static ResponseEntity getResponseFromException(HttpClientErrorException exception){
        ResponseEntity response;
        switch (exception.getStatusCode()){
            case FORBIDDEN:  response = MyResponse.forbidden(); break;
            case BAD_REQUEST: response = MyResponse.badRequest();break;
            case UNAUTHORIZED: response = MyResponse.unauthorized();break;
            default:{
                ResultData resultData = ResultData.error("未知错误");
                response = ResponseEntity.status(exception.getStatusCode()).contentType(MediaType.APPLICATION_JSON).body(resultData);
            }
        }
        return  response;
    }
}
