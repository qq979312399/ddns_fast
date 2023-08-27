package com.gpingguo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.gpingguo.dao.**")
@SpringBootApplication
public class DdnsFastApplication {

    public static void main(String[] args) {
        SpringApplication.run(DdnsFastApplication.class, args);
    }

}
