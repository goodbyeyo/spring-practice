package com.study.spring.mvc;

import com.study.spring.annotation.RequestMethod;
import com.study.spring.mvc.controller.*;

import java.util.HashMap;
import java.util.Map;

public class RequestMappingHandlerMapping implements HandlerMapping {

    private Map<HandlerKey, Controller> mappings = new HashMap<>();

    void init() {
//        mappings.put(new HandlerKey(RequestMethod.GET, "/"), new HomeController());
        mappings.put(new HandlerKey(RequestMethod.GET, "/users"), new UserController());
        mappings.put(new HandlerKey(RequestMethod.POST, "/users"), new UserCreateController());
        mappings.put(new HandlerKey(RequestMethod.GET, "/user/form"), new ForwardController("/user/userForm"));
    }

    public Controller findHandler(HandlerKey handlerKey) {
        // 객체비교 EqualsHashCode 가 필요하다
        return mappings.get(handlerKey);
    }

}
