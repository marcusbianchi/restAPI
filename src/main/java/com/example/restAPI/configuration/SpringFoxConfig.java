package com.example.restAPI.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("QuickPoll REST API")
                .description("QuickPoll Api for creating and managing polls")
                .termsOfServiceUrl("http://example.com/terms-of-service")
                .contact(new Contact("Marcus","www.test.com","teste@gmail.com"))
                .license("MIT License")
                .licenseUrl("http://opensource.org/licenses/MIT")
                .build();
    }

    @Bean
    public Docket v1APIConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("V1")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.restAPI.v1.controller"))
                .paths(regex("/v1/*.*"))
                .build()
                .apiInfo(getApiInfo());
    }

    @Bean
    public Docket v2APIConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("V2")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.restAPI.v2.controller"))
                .paths(regex("/v2/*.*"))
                .build()
                .apiInfo(getApiInfo());
    }

}
