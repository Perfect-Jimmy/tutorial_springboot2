package com.tutorial.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jimmy on 2017/12/14.
 */
@RestController
public class TestController {

    @RequestMapping("/hello")
    public String helloWorld(){
        return "hello";
    }
}
