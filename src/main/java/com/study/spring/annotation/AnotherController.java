package com.study.spring.annotation;

import com.study.spring.annotation.Controller;
import com.study.spring.annotation.RequestMapping;
import com.study.spring.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AnotherController {

    @RequestMapping(value = "/another", method = RequestMethod.POST)
    public String another(HttpServletRequest request, HttpServletResponse response) {
        return "another";
    }

}
