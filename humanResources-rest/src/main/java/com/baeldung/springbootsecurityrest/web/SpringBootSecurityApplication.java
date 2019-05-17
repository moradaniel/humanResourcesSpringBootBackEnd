package com.baeldung.springbootsecurityrest.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"org.humanResources","com.baeldung.springbootsecurityrest"})
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class},
        scanBasePackages = {"org.humanResources","com.baeldung.springbootsecurityrest"})

@EnableJpaRepositories(basePackages = {"org.humanResources","com.baeldung.springbootsecurityrest"})
@EntityScan({"org.humanResources","com.baeldung.springbootsecurityrest"})

public class SpringBootSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSecurityApplication.class, args);
    }
}
