package com.siegfried.springsecuritydemo.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String hello(){
        return "去你妹";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/secured")
    @Secured({"ROLE_ba", "ROLE_manager"})
    public String secured(){
        return "secured";
    }

    @GetMapping("/logout")
    public String logout(){
        return "你已登出";
    }
}
