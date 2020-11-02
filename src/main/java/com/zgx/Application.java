package com.zgx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: WY
 * @create: 2020/11/2
 **/
@ComponentScan({"com.zgx.*"})
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        SpringApplication.run(Application.class,args);
    }
}
