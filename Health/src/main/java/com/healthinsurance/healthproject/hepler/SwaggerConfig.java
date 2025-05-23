package com.healthinsurance.healthproject.hepler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Info;

import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class SwaggerConfig {
	@Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(new Info().title("Health API").version("v1"));
    }
}
