package com.baeldung.springbootsecurityrest.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"org.humanResources","com.baeldung.springbootsecurityrest"})
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class},
        scanBasePackages = "com.baeldung.springbootsecurityrest")

public class SpringBootSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSecurityApplication.class, args);
    }
}
