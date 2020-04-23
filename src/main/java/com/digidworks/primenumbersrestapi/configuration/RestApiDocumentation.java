package com.digidworks.primenumbersrestapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class RestApiDocumentation {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .tags(
                        new Tag("Prime Numbers", "Prime numbers REST API.")
                )
                .select()
                .paths(PathSelectors.ant("/api/prime-numbers/**"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "REST API for running primality checks on numbers.",
                "Spring Boot based REST micro service which allows us to check for primality and find out what's the next prime after a given number.",
                "1",
                null,
                new Contact("Nikolai Tenev", "https://digidworks.com/", "nikolai.tenev@digidworks.com"),
                " Apache License Version 2.0", "http://www.apache.org/licenses/", Collections.emptyList());
    }
}
