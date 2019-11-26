package com.sun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan({"com.sun.mapper"})
@SpringBootApplication
public class DataaopApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataaopApplication.class, args);
    }

}
