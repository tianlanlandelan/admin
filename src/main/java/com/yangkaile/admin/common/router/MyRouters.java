package com.yangkaile.admin.common.router;

import com.yangkaile.admin.common.response.MyResponseReader;
import com.yangkaile.admin.common.util.ConfigUtils;
import com.yangkaile.admin.common.util.StringUtils;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangkaile
 * @date 2018-10-09 09:37:33
 */
public class MyRouters {
    public static Map<Integer,MyRouter> routerMap = new HashMap();
    public static void initRouterMap(List<MyRouter> list){
        routerMap.clear();
        for (MyRouter myRouter :list){
            routerMap.put(myRouter.getId(), myRouter);
        }
    }

    /**
     * 获取一个方法的参数类型和参数列表 格式: 参数类型:参数名,参数类型:参数名......
     * @param method Method对象
     * @return
     */
    public static  String getMethodParametersStr(Method method){
        StringBuilder builder = new StringBuilder();
        LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer
                = new LocalVariableTableParameterNameDiscoverer();
        //取参数名列表
        String[] params = parameterNameDiscoverer.getParameterNames(method);
        //取参数类型列表
        Class[] paramTypes = method.getParameterTypes();

        for (int i = 0; i < params.length; i++) {
            builder.append(paramTypes[i].getSimpleName())
                    .append(":")
                    .append(params[i]);
            if(i < params.length - 1){
                builder.append(",");
            }
        }
        return builder.toString();
    }


    /**
     * 初始化路由
     * 解析传入的Controller对象，解析类名前的@RequestMapping注解
     * 遍历该类的方法：
     * 1.解析有@RouterAttribute注解的方法，
     * 2.将@RouterAttribute和@RequestMapping注解信息取出
     * 3.将方法名、参数类型、参数名取出
     * 4.将解析出的信息组装成一个Router对象
     *
     * @param serviceName 组件名
     * @param controllers Controller类
     * @return
     */
    public static  List<MyRouter> initRouters(String serviceName, Class... controllers){
        cleanByServiceName(serviceName);
        Class[] controllerArray = controllers;
        List<MyRouter> list = new ArrayList<>();
        for(Class t:controllerArray){
            //获取Class上的RequestMapping信息（路由前缀）

            String urlPrefix = getRequestMappingValueStr((RequestMapping)t.getAnnotation(RequestMapping.class));
            //获取Class的方法列表
            Method[] methods = t.getDeclaredMethods();
            for(Method method:methods){
                //获取@RouterAttribute注解信息
                RouterAttribute routerAttribute = method.getAnnotation(RouterAttribute.class);
                if(routerAttribute != null){
                    MyRouter myRouterObject = new MyRouter();
                    myRouterObject.setId(routerAttribute.id());
                    myRouterObject.setName(method.getName() + myRouterObject.getId());
                    myRouterObject.setDescription(routerAttribute.description());
                    myRouterObject.setServiceName(serviceName);
                    myRouterObject.setControllerName(t.getSimpleName());
                    myRouterObject.setMethodName(method.getName());
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    if(StringUtils.isEmpty(urlPrefix)){
                        myRouterObject.setRouterUrl(ConfigUtils.HTTP_UTL_PREFIX + serviceName + getRequestMappingValueStr(requestMapping));
                    }else {
                        myRouterObject.setRouterUrl(ConfigUtils.HTTP_UTL_PREFIX + serviceName + urlPrefix + getRequestMappingValueStr(requestMapping));
                    }
                    myRouterObject.setRequestType(getRequestMappingMethodStr(requestMapping));
                    myRouterObject.setParameters(getMethodParametersStr(method));

                    list.add(myRouterObject);
                    //TODO 注册路由
                    registerRouters(myRouterObject);
                }
            }
        }
        return list;
    }

    private static void cleanByServiceName(String serviceName){
        RestTemplate restTemplate = getRestTemplate();
        restTemplate.getForEntity(
                ConfigUtils.CLEAN_ROUTERS_BY_SERVICENAME + "?serviceName=" + serviceName,String.class);
    }
    private static void registerRouters(MyRouter object){
        RestTemplate restTemplate = getRestTemplate();
        restTemplate.postForObject(ConfigUtils.ROUTER_REGISTER_URL,object,String.class);
    }


    public static List<MyRouter> getRouters(){
        RestTemplate restTemplate = getRestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(ConfigUtils.GET_ROUTERS_URL,String.class);
        List<MyRouter> list = MyResponseReader.getList(responseEntity,MyRouter.class);
        MyRouters.initRouterMap(list);
        return list;
    }

    public static  String getRouterUrl(Integer id){
        if(routerMap == null || routerMap.isEmpty()){
            getRouters();
        }
       return routerMap.get(id).getRouterUrl();
    }

    public static RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }


    /**
     * 获取RequestMapping里的value属性，并拼成一个字符串
     * @param requestMapping
     * @return
     */
    public  static String getRequestMappingValueStr(RequestMapping requestMapping){
        if(requestMapping != null){
            StringBuilder requestMappingValues = new StringBuilder();
            for(String string:requestMapping.value()){
                requestMappingValues.append(string);
            }
            return requestMappingValues.toString();
        }
        return null;
    }

    /**
     *  获取RequestMapping里的method属性，并拼成一个字符串
     * @param requestMapping
     * @return
     */
    public static  String getRequestMappingMethodStr(RequestMapping requestMapping){
        if(requestMapping != null){
            StringBuilder requestMappingMethods = new StringBuilder();
            for(RequestMethod requestMethod:requestMapping.method()){
                requestMappingMethods.append(requestMethod);
            }
            return requestMappingMethods.toString();
        }
        return null;
    }

    public static void  main(String[] args){
        System.out.println(getRouterUrl(MyUserManagerRouter.LOGON));
    }

}

