package com.zx.ace.demoui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Administrator on 2017/8/20 0020.
 */
@Controller
@RequestMapping("/zx")
public class TestController {

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Map<String, Object> map){
        map.put("hello", "from TemplateController.hello");
        return "zxtest";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Map<String, Object> map){
        map.put("hello", "from TemplateController.hello");
        return "ace/login";
    }

    @RequestMapping(value = "/error-404", method = RequestMethod.GET)
    public String error(Map<String, Object> map){
        map.put("hello", "from TemplateController.hello");
        return "ace/error-404";
    }
}
