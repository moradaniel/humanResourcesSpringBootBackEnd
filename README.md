## Technologies

- Java 1.8
- Spring Boot 2.x.x
- Hibernate 
- Database Oracle 11g 



### Development

JVM optimization options
```-noverify or -Xverify:none```: Disables the bytecode verification. So the classloader won’t check the bytecode for dangerous or disallowed behavior.
```-XX:TieredStopAtLevel=1```: Limits the optimizations of the HotSpot compiler and its runtime overhead.


https://phauer.com/2017/increase-jvm-development-productivity/





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



### Angular pagination
https://www.bezkoder.com/angular-13-pagination-ngx/

TODO
Add AWS support with Localstack
https://www.youtube.com/watch?v=FOzAdoxdnSc


Resources

https://www.baeldung.com/spring-security-login-angular
https://www.baeldung.com/spring-http-logging

https://grokonez.com/spring-framework/spring-security/angular-spring-boot-jwt-authentication-example-angular-6-spring-security-mysql-full-stack-part-1-overview-and-architecture


```
grant create session to HUMAN_RESOURCES;
grant create session to HUMAN_RESOURCES_TEST;
```