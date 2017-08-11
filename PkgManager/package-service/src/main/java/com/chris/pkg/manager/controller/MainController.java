package com.chris.pkg.manager.controller;

import com.alibaba.fastjson.JSON;
import com.chris.pkg.manager.service.MainService;
import com.chris.pkg.manager.system.MobileHttpServletRequestWrapper;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    private static final String DEFAULT_CHARSET = "UTF-8";
    @Autowired
    private ApplicationContext context;
    private static Map<String, Class<?>> maps = new HashMap();

    static {
        maps.put("MainService/fileUpload", MainService.class);
        maps.put("MainService/getMenus", MainService.class);
        maps.put("MainService/getNewestVersionByIdentifier", MainService.class);
        maps.put("MainService/getVersions", MainService.class);
        maps.put("MainService/getApps", MainService.class);
    }

    @RequestMapping({"/{serviceName}/{methodName}"})
    @ResponseBody
    public Object handle(HttpServletRequest request, @PathVariable String serviceName, @PathVariable String methodName) {
        log.error("{}/{}", serviceName, methodName);

        String parameterInput = null;
        Map<String, Object> resultMap = ((MobileHttpServletRequestWrapper) request).getParametersMap();
        if (resultMap.size() == 0) {
            try {
                parameterInput = IOUtils.toString(request.getInputStream(), "UTF-8");
            } catch (Exception localException) {
            }
        } else {
            parameterInput = JSON.toJSONString(resultMap);
        }
        Object result = invokes(serviceName, methodName, parameterInput);
        log.error("result(json) = " + JSON.toJSONString(result));
        return result;
    }

    private Object invokes(String serviceName, String methodName, String parameterInput) {
        Object result = null;
        Class<?> clazz = (Class) maps.get(String.format("%s/%s", serviceName, methodName));
        for (Method method : clazz.getMethods()) {
            if (method.getName().equals(methodName)) {
                Type[] types = method.getGenericParameterTypes();
                List<String> paramList = new ArrayList<>();
                if (types.length == 1) {
                    paramList.add(parameterInput);
                } else if (types.length > 1) {
                    paramList.addAll(JSON.parseArray(parameterInput, String.class));
                }
                if (types.length == paramList.size()) {
                    List<Object> params = new ArrayList<>();
                    for (int i = 0; i < types.length; i++) {
                        params.add(JSON.parseObject(paramList.get(i), types[i]));
                    }
                    try {
                        result = method.invoke(this.context.getBean(clazz), params.toArray());
                    } catch (Exception e) {
                        log.error(e.toString());
                    }
                }
            }
        }
        return result;
    }
}
