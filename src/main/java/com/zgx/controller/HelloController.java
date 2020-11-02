package com.zgx.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author: WY
 * @create: 2020/11/2
 **/
@RestController
public class HelloController {

@Resource
private RestTemplate restTemplate;

    @Value("${server.port}")
    private String port;

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello world ! "+port;
    }
}
