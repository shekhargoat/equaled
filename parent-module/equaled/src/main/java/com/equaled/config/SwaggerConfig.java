package com.equaled.config;

import javax.servlet.ServletContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
    public Docket api(ServletContext servletContext) {
        return new Docket(DocumentationType.SWAGGER_2)
        		.select().apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error")))
                .paths(Predicates.not(PathSelectors.ant("/actuator/**")))
                .build().apiInfo(apiInfo())
                .useDefaultResponseMessages(false);

    }
	
	private ApiInfo apiInfo() {
        @SuppressWarnings("deprecation")
		ApiInfo apiInfo = new ApiInfo("EqualEd Management",
                "API documentation for EqualEd management.",
                "V 0.0.1", "", "Equal Ed",
                "@Copyright EqualEd 2024.", "");
        return apiInfo;
    }
}
