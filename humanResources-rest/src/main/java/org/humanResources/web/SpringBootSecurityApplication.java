package org.humanResources.web;

import com.cosium.spring.data.jpa.entity.graph.repository.support.EntityGraphJpaRepositoryFactoryBean;
import org.humanResources.repository.asimio.AsimioSimpleJpaRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"org.humanResources"})
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class},
        scanBasePackages = {"org.humanResources"})

//https://github.com/Cosium/spring-data-jpa-entity-graph
@EnableJpaRepositories(basePackages = {"org.humanResources"},
        repositoryFactoryBeanClass = EntityGraphJpaRepositoryFactoryBean.class,
        repositoryBaseClass = AsimioSimpleJpaRepository.class)

@EntityScan({"org.humanResources"})

public class SpringBootSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSecurityApplication.class, args);
    }
}
