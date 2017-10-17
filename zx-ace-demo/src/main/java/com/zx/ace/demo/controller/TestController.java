package com.zx.ace.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/8/20 0020.
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
}
