package com.multiproject.pager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @RequestMapping("/home/index")
    public  String index(){
        return  "hello world";
    }
}

