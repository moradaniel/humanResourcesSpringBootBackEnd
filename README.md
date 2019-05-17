## Technologies

- Java 1.8
- Spring Boot 2.x.x
- Hibernate 
- Database Oracle 11g 


Rest:

### Swagger
```
http://localhost:8082/humanResources-rest/swagger-ui.html#/
```

-Integration tests
-Stateless API with JWT



Features

-Logging requests with CommonsRequestLoggingFilter



### Run a jar file from console
```
$java -cp build/libs/humanResources.jar org.humanResources.service.Main Reader
```

### Install Oracle dependencies
```
mvn install:install-file -Dfile=ojdbc7.jar  -DgroupId=com.oracle -DartifactId=ojdbc7 -Dversion=12.1.0.1 -Dpackaging=jar
```

## Testing
https://www.petrikainulainen.net/programming/maven/integration-testing-with-maven/
https://www.javaworld.com/article/2074569/core-java/unit-and-integration-tests-with-maven-and-junit-categories.html

### Unit testing

$mvn clean test -Pdevelopment

### Integration testing
$mvn clean verify -Pintegration-test


## Maven commands

### Build humanresources-core

To install all projects:

```
/parent/$mvn clean install
```

To avoid running the tests

/parent/$mvn clean install -DskipTests

For Reinke use the "reinke" profile defined in parent-pom

/path/to/ranch/projects/parent-pom$ mvn clean install -DskipTests -Preinke





TODO
Add AWS support with Localstack
https://www.youtube.com/watch?v=FOzAdoxdnSc


Resources

https://www.baeldung.com/spring-security-login-angular
https://www.baeldung.com/spring-http-logging

https://grokonez.com/spring-framework/spring-security/angular-spring-boot-jwt-authentication-example-angular-6-spring-security-mysql-full-stack-part-1-overview-and-architecture