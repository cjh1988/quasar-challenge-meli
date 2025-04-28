package com.meli.challenge.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Quasar Challenge API - MELI")
                        .description("API Documentation")
                        .version("1.0")
                        .contact(new Contact().name("meli").email("quasar@meli.com.ar"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }

}
