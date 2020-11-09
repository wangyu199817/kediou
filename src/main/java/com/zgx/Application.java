package com.zgx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.MultipartConfigElement;
import java.io.File;

/**
 * @author: WY
 * @create: 2020/11/2
 **/
@ComponentScan({"com.zgx.*"})
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("20MB");
        factory.setMaxRequestSize("30MB");
        // 文件临时保存目录 避免占用服务器 temp 目录
        //windows 系统下文件目录
        String path="d://tempss";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        factory.setLocation(path);
        return factory.createMultipartConfig();
    }
}
