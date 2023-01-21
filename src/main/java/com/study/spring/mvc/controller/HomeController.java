package com.study.spring.mvc.controller;

import com.study.spring.annotation.RequestMethod;
import com.study.spring.mvc.annotation.Controller;
import com.study.spring.mvc.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// public class HomeController implements Controller{
//    @Override
@Controller
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "home";
    }
}
