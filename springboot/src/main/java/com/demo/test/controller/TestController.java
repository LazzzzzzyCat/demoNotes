package com.demo.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huwj
 * @date 2020-08-04 13:52
 */
@RequestMapping("test")
@RestController
public class TestController {

    @GetMapping("hello")
    public String hello(){
        return "hello";
    }
}
